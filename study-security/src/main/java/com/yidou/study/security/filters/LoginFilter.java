package com.yidou.study.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginFileter
 * @Description JSON格式登录过滤器
 * @Author Lipeng5
 * @Date 2023/7/17 16:27
 * @Version 1.0
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {


	public LoginFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String contentType = request.getContentType();
        if(contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            HashMap<String, String> userInfo = new HashMap<>();
            try {
                userInfo = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
                String username = obtainUsername(userInfo);
                username = (username != null) ? username.trim() : "";
                String password = obtainPassword(userInfo);
                password = (password != null) ? password : "";
                UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                        password);
                // Allow subclasses to set the "details" property
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (Exception e) {
                logger.error("JSON格式登录认证失败");
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }


    @Nullable
    protected String obtainPassword(Map<String, String> userInfo) {
        return userInfo.get(this.getPasswordParameter());
    }

    @Nullable
    protected String obtainUsername(Map<String, String> userInfo) {
        return userInfo.get(this.getUsernameParameter());
    }
}
