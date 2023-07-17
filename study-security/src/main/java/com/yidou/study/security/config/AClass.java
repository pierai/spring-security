package com.yidou.study.security.config;

import jakarta.servlet.Filter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class AClass implements HttpSecurityBuilder<AClass> {
	@Override
	public <C extends SecurityConfigurer<DefaultSecurityFilterChain, AClass>> C getConfigurer(Class<C> clazz) {
		return null;
	}

	@Override
	public <C extends SecurityConfigurer<DefaultSecurityFilterChain, AClass>> C removeConfigurer(Class<C> clazz) {
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
	public AClass authenticationProvider(AuthenticationProvider authenticationProvider) {
		return null;
	}

	@Override
	public AClass userDetailsService(UserDetailsService userDetailsService) throws Exception {
		return null;
	}

	@Override
	public AClass addFilterAfter(Filter filter, Class<? extends Filter> afterFilter) {
		return null;
	}

	@Override
	public AClass addFilterBefore(Filter filter, Class<? extends Filter> beforeFilter) {
		return null;
	}

	@Override
	public AClass addFilter(Filter filter) {
		return null;
	}

	@Override
	public DefaultSecurityFilterChain build() throws Exception {
		return null;
	}
}
