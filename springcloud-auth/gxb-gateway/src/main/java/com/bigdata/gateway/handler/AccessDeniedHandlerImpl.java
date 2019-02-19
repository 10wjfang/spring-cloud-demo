package com.bigdata.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.common.enums.ErrorCodeEnum;
import com.bigdata.common.manger.ResponseGenerator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过的用户访问无权限资源时的异常
 *
 * @author fwj
 * @date 2019-02-12 18:40
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSONObject.toJSON(ResponseGenerator.error(ErrorCodeEnum.FORBIDDEN)));
    }
}
