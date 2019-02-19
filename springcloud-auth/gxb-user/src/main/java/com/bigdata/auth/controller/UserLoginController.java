package com.bigdata.auth.controller;

import com.bigdata.common.config.Constants;
import com.bigdata.common.dto.ResponseDataDTO;
import com.bigdata.common.enums.ErrorCodeEnum;
import com.bigdata.auth.domain.TUserAuthEntity;
import com.bigdata.auth.service.IUserAuthService;
import com.bigdata.auth.view.LoginInfoVo;
import com.bigdata.common.manger.ResponseGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录
 *
 * @author fwj
 * @date 2019-02-12 19:09
 **/
@RestController
public class UserLoginController {
    @Autowired
    private IUserAuthService userAuthService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @RequestMapping(value = "/login")
    public ResponseDataDTO<LoginInfoVo> UserLogin(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = null;
        try {
            String token = UUID.randomUUID().toString();
            authenticate = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            TUserAuthEntity entity = userAuthService.getUserAuth(username);
            redisTemplate.opsForHash().put(token, Constants.KEY_USER_INFO, authenticationToken);
            redisTemplate.opsForHash().put(token, Constants.KEY_SPRING_SECURITY_CONTEXT, SecurityContextHolder.getContext());
            redisTemplate.expire(token, Constants.TOKEN_EXPIRE, TimeUnit.MINUTES);
            LoginInfoVo loginInfoVo = new LoginInfoVo();
            BeanUtils.copyProperties(entity, loginInfoVo);
            loginInfoVo.setToken(token);
            return ResponseGenerator.success(loginInfoVo);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return ResponseGenerator.errors(ErrorCodeEnum.USERNAME_PASSWORD_ERROR);
    }
}
