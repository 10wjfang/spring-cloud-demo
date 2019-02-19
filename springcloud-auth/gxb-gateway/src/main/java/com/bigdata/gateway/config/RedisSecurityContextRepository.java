package com.bigdata.gateway.config;

import com.bigdata.common.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.ClassUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 把security原来token放在HttpSession(session)里的方式重新自定义为放到Redis里
 *
 * @author fwj
 * @date 2019-02-18 14:27
 **/
public class RedisSecurityContextRepository implements SecurityContextRepository {
    private final Logger logger = LoggerFactory.getLogger(RedisSecurityContextRepository.class);
    private final RedisTemplate<String, Serializable> redisTemplate;
    private boolean isServlet3 = ClassUtils.hasMethod(ServletRequest.class, "startAsync");
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private boolean disableUrlRewriting = false;

    public RedisSecurityContextRepository(final RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        logger.debug("url: {}", request.getRequestURI());
        HttpServletResponse response = requestResponseHolder.getResponse();
        String token = extractToken(request);
        SecurityContext context = this.readSecurityContextFromRedis(token);
        if (context == null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("No SecurityContext was available from the Redis: " + token + ". "
                        + "A new one will be created.");
            }

            context = this.generateNewContext();
        }

        SaveToRedisResponseWrapper wrappedResponse = new SaveToRedisResponseWrapper(response, request, context);
        requestResponseHolder.setResponse(wrappedResponse);
        if (this.isServlet3) {
            requestResponseHolder.setRequest(new Servlet3SaveToRedisRequestWrapper(request, wrappedResponse));
        }

        return context;
    }

    private SecurityContext generateNewContext() {
        return SecurityContextHolder.createEmptyContext();
    }

    private SecurityContext readSecurityContextFromRedis(String token) {
        boolean debug = this.logger.isDebugEnabled();
        if (token == null) {
            if (debug) {
                this.logger.debug("No Token currently exists");
            }

            return null;
        } else {
            Object contextFromRedis = loadContextFromRedis(token);
            if (contextFromRedis == null) {
                if (debug) {
                    this.logger.debug("Token returned null object for SPRING_SECURITY_CONTEXT");
                }

                return null;
            } else if (!(contextFromRedis instanceof SecurityContext)) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn(Constants.KEY_SPRING_SECURITY_CONTEXT
                            + " did not contain a SecurityContext but contained: '" + contextFromRedis
                            + "'; are you improperly modifying the HttpSession directly "
                            + "(you should always use SecurityContextHolder) or using the HttpSession attribute "
                            + "reserved for this class?");
                }

                return null;
            } else {
                if (debug) {
                    this.logger.debug("Obtained a valid SecurityContext from "
                            + Constants.KEY_SPRING_SECURITY_CONTEXT + ": '" + contextFromRedis + "'");
                }
                redisTemplate.expire(token, Constants.TOKEN_EXPIRE, TimeUnit.MINUTES);
                return (SecurityContext)contextFromRedis;
            }
        }
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        SaveToRedisResponseWrapper responseWrapper = WebUtils.getNativeResponse(response,
                SaveToRedisResponseWrapper.class);
        if (responseWrapper == null) {
            throw new IllegalStateException("Cannot invoke saveContext on response " + response
                    + ". You must use the HttpRequestResponseHolder.response after invoking loadContext");
        } else {
            if (!responseWrapper.isContextSaved()) {
                responseWrapper.saveContext(context);
            }

        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) {
            return false;
        }
        return loadContextFromRedis(token) != null;
    }

    final class SaveToRedisResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {
        private final HttpServletRequest request;
        private final SecurityContext contextBeforeExecution;
        private final Authentication authBeforeExecution;

        public SaveToRedisResponseWrapper(HttpServletResponse response, HttpServletRequest request,
                                          SecurityContext context) {
            super(response, disableUrlRewriting);
            this.request = request;
            this.contextBeforeExecution = context;
            this.authBeforeExecution = context.getAuthentication();
        }

        @Override
        protected void saveContext(SecurityContext context) {
            Authentication authentication = context.getAuthentication();
            String token = extractToken(request);
            if (authentication == null || trustResolver.isAnonymous(authentication)) {
                logger.debug("Security is empty or content is anonymous.");
                return;
            }
            if (token != null) {
                if (contextChanged(context) || loadContextFromRedis(token) == null) {
                    saveContextToRedis(token, context);
                }
            }
        }

        private boolean contextChanged(SecurityContext context) {
            return context != contextBeforeExecution || context.getAuthentication() != authBeforeExecution;
        }
    }

    private void saveContextToRedis(String token, SecurityContext context) {
        redisTemplate.opsForHash().put(token, Constants.KEY_SPRING_SECURITY_CONTEXT, context);
        redisTemplate.expire(token, Constants.TOKEN_EXPIRE, TimeUnit.MINUTES);
    }

    private Object loadContextFromRedis(String token) {
        return redisTemplate.opsForHash().get(token, Constants.KEY_SPRING_SECURITY_CONTEXT);
    }

    /**
     * 获取Token
     * @param request
     * @return
     */
    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_TOKEN);
        if (token == null) {
            logger.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter(Constants.HEADER_TOKEN);
            if (token == null) {
                logger.debug("Token not found in parameters");
            }
        }
        return token;
    }

    private class Servlet3SaveToRedisRequestWrapper extends HttpServletRequestWrapper {
        private final SaveContextOnUpdateOrErrorResponseWrapper response;
        public Servlet3SaveToRedisRequestWrapper(HttpServletRequest request,
                                                 SaveContextOnUpdateOrErrorResponseWrapper wrappedResponse) {
            super(request);
            this.response = wrappedResponse;
        }

        @Override
        public AsyncContext startAsync() {
            this.response.disableSaveOnResponseCommitted();
            return super.startAsync();
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
                throws IllegalStateException {
            this.response.disableSaveOnResponseCommitted();
            return super.startAsync(servletRequest, servletResponse);
        }
    }
}
