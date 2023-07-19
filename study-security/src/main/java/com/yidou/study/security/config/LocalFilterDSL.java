package com.yidou.study.security.config;

import com.yidou.study.security.filters.LoginFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 局部 LocalAuthenticationManager
 */
public class LocalFilterDSL extends AbstractHttpConfigurer<LocalFilterDSL, HttpSecurity> {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 获取局部 authenticationManager
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

		/**
		 * 插入过滤器到FilterChain
		 */
		LoginFilter loginFilter = new LoginFilter(authenticationManager);
		loginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/sfc1/doLogin",
				"POST"));
		http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
	}

//  public static MyCustomDsl customDsl() {
//     return new MyCustomDsl();
//  }
}
