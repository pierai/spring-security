package com.yidou.study.security.config;

import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class MySecurityConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, MyHttpSecurityBuilder> {
	@Override
	public void init(MyHttpSecurityBuilder builder) throws Exception {
//		builder.getConfigurer()；
	}

	@Override
	public void configure(MyHttpSecurityBuilder builder) throws Exception {

	}
}
