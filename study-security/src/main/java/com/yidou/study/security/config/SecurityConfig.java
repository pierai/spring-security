package com.yidou.study.security.config;

import com.yidou.study.security.handler.MyAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private UserDetailsPasswordService userDetailsPasswordService;

    /*@Bean
    SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
       // 局部 AuthenticationManager的用户数据源
       InMemoryUserDetailsManager localityUserDetailsService = new InMemoryUserDetailsManager();
       localityUserDetailsService.createUser(
             User.withUsername("lisi").password("{noop}123456")
                   .roles("LOCAL")
                   .build());
       http.securityMatcher("/sfc2/**")
             .authorizeRequests()
             .anyRequest().authenticated()
             //表单登录配置项
             .and()
             .formLogin()
             .loginPage("/mylogin")    //自定义登录页的位置
             .loginProcessingUrl("/sfc2/doLogin")    //登录接口地址
//             .defaultSuccessUrl("/dsUrl")  //登录成功跳转页。指定了successHandler就不要再使用defaultSuccessUrl()方法了
//           .failureUrl("/mylogin.html")   //登陆失败跳转页：前端重定向
//           .successHandler(this.successHandler())
             .failureHandler(new MyAuthenticationFailureHandler())
//             .usernameParameter("uname")    //用户名参数名称
//             .passwordParameter("passwd")   //密码参数名称
             .withObjectPostProcessor(new ObjectPostProcessor<UsernamePasswordAuthenticationFilter>() {
                @Override
                public <O extends UsernamePasswordAuthenticationFilter> O postProcess(O object) {
                   object.setUsernameParameter("username");
                   object.setPasswordParameter("password");
                   object.setAuthenticationSuccessHandler((request, response, authentication) -> {
                      response.getWriter().write("sfc2 login success !!!");
                   });
                   return object;
                }
             })
             .permitAll()
             .and()
             .anonymous()
             .and()
             .userDetailsService(localityUserDetailsService)
             .logout()
             .logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout1")))
             .and()
             .csrf().disable();
       return http.build();
    }*/


	@Bean
	SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
		/*
		   局部 AuthenticationManager：从头定义ProviderManager、AuthenticationProvider、UserDetailsService实例
		   	  1. 多数据源：内存数据源、mysql数据源
		*/
		DaoAuthenticationProvider localProvider = new DaoAuthenticationProvider();
		localProvider.setUserDetailsService(localityUserDetailsService());
		DaoAuthenticationProvider localProvider2 = new DaoAuthenticationProvider();
		localProvider2.setUserDetailsService(userDetailsService);
		// 指定密码升级服务
		localProvider2.setUserDetailsPasswordService(userDetailsPasswordService);
		List<AuthenticationProvider> providers = new ArrayList<>(2);
		providers.add(localProvider);
		providers.add(localProvider2);
		//指定 全局AuthenticationManager
		ProviderManager localityAuthenticationManager = new ProviderManager(providers, authenticationConfiguration.getAuthenticationManager());

		System.out.println("局部：" + localityAuthenticationManager);
		System.out.println("全局：" + authenticationConfiguration.getAuthenticationManager());
		http.apply(new LocalFilterDSL())
				.and()
				.securityMatcher("/sfc1/**")
				.authorizeRequests()
				.anyRequest().authenticated()
				//表单登录配置项
				.and()
				.formLogin()
				.loginPage("/mylogin")    //自定义登录页的位置
				.loginProcessingUrl("/sfc1/doLogin")    //登录接口地址
//             .defaultSuccessUrl("/dsUrl")  //登录成功跳转页。指定了successHandler就不要再使用defaultSuccessUrl()方法了
//           .failureUrl("/mylogin.html")   //登陆失败跳转页：前端重定向
//           .successHandler(this.successHandler())
				.failureHandler(new MyAuthenticationFailureHandler())
//             .usernameParameter("uname")    //用户名参数名称
//             .passwordParameter("passwd")   //密码参数名称
				.withObjectPostProcessor(new ObjectPostProcessor<UsernamePasswordAuthenticationFilter>() {
					@Override
					public <O extends UsernamePasswordAuthenticationFilter> O postProcess(O object) {
						object.setUsernameParameter("username");
						object.setPasswordParameter("password");
						object.setAuthenticationSuccessHandler((request, response, authentication) -> {
							response.getWriter().write("sfc1 login success !!!");
						});
						return object;
					}
				})
				.permitAll()
				.and()
				.anonymous()
				.and()
				// AuthenticationManager
				.authenticationManager(localityAuthenticationManager)
				.logout()
				.logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout1")))
				.and()
				.csrf().disable();
		return http.build();
	}

	/**
	 * 指定加密方式:
	 * 	 1. 不指定时默认使用的DelegatingPasswordEncoder也是 BCrypt加密；当密码的前缀不是bcrypt时，则升级密码
	 */
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

    /* 错误示例： 无法使用这种方式配置AuthenticationManagerBuilder
         不推荐使用此方式配置全局AuthenticationManager，放开注释会报错
         即使没有@Bean定义全局AuthenticationManager，放开注释也会报错
     */
//    @Autowired
//    public void authManager(AuthenticationManagerBuilder builder) throws Exception {
//        System.out.println("默认全局=="+builder);
//        builder.userDetailsService(globalUserDetailsService());
//    }

	/**
	 * 获取过滤器链sfc1的局部AuthenticationManager
	 1. 不建议将局部AuthenticationManager 通过@Bean定义，会和全局AuthenticationManager冲突
	 */
//  @Bean
//  public AuthenticationManager localityAuthenticationManager(HttpSecurity http) throws Exception {
//     return http.
//           getSharedObject(AuthenticationManagerBuilder.class)
//           .userDetailsService(localityUserDetailsService())
//           .and()
//           .build();
//  }

	/**
	 * 自定义全局AuthenticationManager
	 */
	@Bean
	public AuthenticationManager globalAuthenticationManager() throws Exception {
		// 全局 AuthenticationManager
		DaoAuthenticationProvider globalProvider = new DaoAuthenticationProvider();
		globalProvider.setUserDetailsService(globalUserDetailsService());
		return new ProviderManager(globalProvider);
	}


	/**
	 * 全局AuthenticationMananger中定义的用户
	 * 1. InMemoryUserDetailsManager是UserDetailsService的子类
	 * 2. 返回值类型必须是 UserDetailsService 才能作为AuthenticationManager的数据源注入进去
	 * 3. 使用自定义AuthenticationProvider时，从配置文件中删除UserDetailsService bean创建。否则会StackOverflowError
	 * 也就是说，如果@Bean自定义了AuthenticationManager，那么 就不会再自动将UserDetailsService 注入AuthenticationManager，并且会报错
	 */
//  @Bean
//  UserDetailsService globalUserDetailsService() {
//     InMemoryUserDetailsManager globalUserDetailsService = new InMemoryUserDetailsManager();
//     globalUserDetailsService.createUser(
//           User.withUsername("wangwu").password("{noop}123456")
//                 .roles("LOCAL")
//                 .build());
//     return globalUserDetailsService;
//  }
	@Bean
	InMemoryUserDetailsManager globalUserDetailsService() {
		InMemoryUserDetailsManager globalUserDetailsService = new InMemoryUserDetailsManager();
		globalUserDetailsService.createUser(
				User.withUsername("wangwu").password("{noop}123456")
						.roles("LOCAL")
						.build());
		return globalUserDetailsService;
	}

	/**
	 * 局部 AuthenticationManager的用户数据源：构造基于内存的数据源管理
	 * 1. 返回值不能为UserDetailsService，否则将会被识别为 全局AuthenticationMananger中定义的用户
	 */
	@Bean
	public InMemoryUserDetailsManager localityUserDetailsService() {
		// 局部 AuthenticationManager的用户数据源
		InMemoryUserDetailsManager localityUserDetailsService = new InMemoryUserDetailsManager();
		localityUserDetailsService.createUser(
				User.withUsername("zhaoliu").password("{noop}123456")
						.roles("LOCAL")
						.build());
		return localityUserDetailsService;
	}

	/**
	 * 登录成功后处理事项：
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
