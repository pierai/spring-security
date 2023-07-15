package com.yidou.study.security.config;

import com.yidou.study.security.handler.MyAuthenticationFailureHandler;
import com.yidou.study.security.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().authenticated()
				//表单登录配置项
				.and()
				.formLogin()
				// 指定登录页通过访问后台接口返回
				.loginPage("/login")
				//登录接口地址
				.loginProcessingUrl("/doLogin")
				.usernameParameter("uname")    //用户名参数名称
				.passwordParameter("passwd")   //密码参数名称
				//登录成功处理
				.successHandler(new MyAuthenticationSuccessHandler())
				//登录失败处理
				.failureHandler(new MyAuthenticationFailureHandler())
				.permitAll()
				.and()
				//注销
				.logout()
				.logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout1")))
				.and()
				// 配置 ProviderManager
				.authenticationManager(authenticationManager())
				.csrf().disable();
		return http.build();
	}

	/**
	 * 配置登录成功事项：配置targetUrlParameter和defaultTargetUrl，并在HttpSecurity.successHandler(successHandler())中指定.
	 */
	@Bean
	SavedRequestAwareAuthenticationSuccessHandler successHandler() {
		SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		authenticationSuccessHandler.setTargetUrlParameter("target");
		authenticationSuccessHandler.setDefaultTargetUrl("/user");
		return authenticationSuccessHandler;
	}


	/**
	 * 配置ProviderManager
	 */
	@Bean
	AuthenticationManager authenticationManager() {
		//指定认证方式：DaoAuthenticationProvider表示用户名/密码认证
		DaoAuthenticationProvider ap1 = new DaoAuthenticationProvider();
		ap1.setUserDetailsService(uds1());
		DaoAuthenticationProvider ap2 = new DaoAuthenticationProvider();
		ap2.setUserDetailsService(uds2());
		return new ProviderManager(ap1, ap2);
	}

	/**
	 * 配置用户数据源1
	 */
	@Bean
	UserDetailsService uds1() {
		InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager();
		userDetailService.createUser(User.withUsername("zhangsan").password("{noop}zhangsan").roles("admin").build());
		return userDetailService;
	}

	/**
	 * 配置用户数据源2
	 */
	@Bean
	UserDetailsService uds2() {
		InMemoryUserDetailsManager userDetailService = new InMemoryUserDetailsManager();
		userDetailService.createUser(User.withUsername("lisi").password("{noop}lisi").roles("visitor").build());
		return userDetailService;
	}
}

