package com.yidou.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAuthenticationFailureHandler implements
        AuthenticationFailureHandler {
       @Override
       public void onAuthenticationFailure(HttpServletRequest request,
                                           HttpServletResponse response,
                                           AuthenticationException exception)
                                                  throws IOException, ServletException {
           response.setContentType("application/json;charset=utf-8");
           Map<String, Object> resp = new HashMap<>();
           resp.put("status", 500);
           resp.put("msg", "登录失败!" + exception.getMessage());
           ObjectMapper om = new ObjectMapper();
           String s = om.writeValueAsString(resp);
           response.getWriter().write(s);
       }
    }
