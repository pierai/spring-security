package com.yidou.study.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {
	/**
	 *
	 * @return 返回一个thymeleaf登录模板
	 */
	@RequestMapping("/login")
	public String loginView() {
		return "login";
	}
}
