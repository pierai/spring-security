package com.yidou.study.security.config;

import jakarta.servlet.Filter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class MyHttpSecurityBuilder implements HttpSecurityBuilder<MyHttpSecurityBuilder> {
	public static void main(String[] args) {
		MyHttpSecurityBuilder b = new MyHttpSecurityBuilder();
		// 传入一个配置类， 能初始化SecurityBuilder<DefaultSecurityFilterChain>和配置
//		b.getConfigurer(MySecurityConfigurer.class)
	}
	@Override
	public <C extends SecurityConfigurer<DefaultSecurityFilterChain, MyHttpSecurityBuilder>> C getConfigurer(Class<C> clazz) {
		return null;
	}

	@Override
	public <C extends SecurityConfigurer<DefaultSecurityFilterChain, MyHttpSecurityBuilder>> C removeConfigurer(Class<C> clazz) {
		return null;
	}

	@Override
	public <C> void setSharedObject(Class<C> sharedType, C object) {

	}

	@Override
	public <C> C getSharedObject(Class<C> sharedType) {
		return null;
	}

	@Override
	public MyHttpSecurityBuilder authenticationProvider(AuthenticationProvider authenticationProvider) {
		return null;
	}

	@Override
	public MyHttpSecurityBuilder userDetailsService(UserDetailsService userDetailsService) throws Exception {
		return null;
	}

	@Override
	public MyHttpSecurityBuilder addFilterAfter(Filter filter, Class<? extends Filter> afterFilter) {
		return null;
	}

	@Override
	public MyHttpSecurityBuilder addFilterBefore(Filter filter, Class<? extends Filter> beforeFilter) {
		return null;
	}

	@Override
	public MyHttpSecurityBuilder addFilter(Filter filter) {
		return null;
	}

	@Override
	public DefaultSecurityFilterChain build() throws Exception {
		return null;
	}
}
