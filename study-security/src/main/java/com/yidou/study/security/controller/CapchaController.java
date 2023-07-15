package com.yidou.study.security.controller;

import com.google.code.kaptcha.Producer;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


@Controller
public class CapchaController {
	@Autowired
	Producer producer;

	@GetMapping("/vc.jpg")
	public void getVerifyCode(HttpServletResponse resp, HttpSession session) throws IOException {
		resp.setContentType("image/jpeg");
		String text = producer.createText();
		session.setAttribute("kaptcha", text);
		BufferedImage image = producer.createImage(text);
		try(ServletOutputStream out = resp.getOutputStream()) {
			ImageIO.write(image, "jpg", out);
		}
	}
}
