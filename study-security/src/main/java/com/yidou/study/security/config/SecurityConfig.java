package com.yidou.study.security.config;

import com.yidou.study.security.handler.MyAuthenticationFailureHandler;
import com.yidou.study.security.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
				// 登录页通过访问接口返回
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
				.csrf().disable();
		return http.build();
	}

	/**
	 * 配置targetUrlParameter和defaultTargetUrl，并在HttpSecurity.successHandler(successHandler())中指定.
	 */
	@Bean
	SavedRequestAwareAuthenticationSuccessHandler successHandler() {
		SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		authenticationSuccessHandler.setTargetUrlParameter("target");
		authenticationSuccessHandler.setDefaultTargetUrl("/user");
		return authenticationSuccessHandler;
	}
}

