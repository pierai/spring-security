package com.yidou.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
       @Override
       public void onAuthenticationSuccess(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Authentication authentication)
                                                  throws IOException, ServletException {
           response.setContentType("application/json;charset=utf-8");
           Map<String, Object> resp = new HashMap<>();
           resp.put("status", 200);
           resp.put("msg", "登录成功!");
           ObjectMapper om = new ObjectMapper();
           String s = om.writeValueAsString(resp);
           response.getWriter().write(s);
       }
    }
