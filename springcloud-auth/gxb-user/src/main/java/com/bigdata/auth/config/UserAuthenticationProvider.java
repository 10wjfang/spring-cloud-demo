package com.bigdata.auth.config;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.auth.service.CustomUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 用户认证
 *
 * @author fwj
 * @date 2019-02-12 22:11
 **/
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);
    @Autowired
    private CustomUserService customUserService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug(JSONObject.toJSON(authentication).toString());
        UserDetails userDetails = customUserService.loadUserByUsername(authentication.getPrincipal().toString());
        if (userDetails == null) {
            throw new BadCredentialsException("Username not found!");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities());
        token.setDetails(userDetails);
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
