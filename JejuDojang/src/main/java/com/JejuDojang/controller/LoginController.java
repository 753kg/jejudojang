package com.JejuDojang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.JejuDojang.config.auth.LoginUser;
import com.JejuDojang.config.auth.dto.SessionUser;

import lombok.extern.java.Log;

@Log
@Controller
public class LoginController {

	//private final HttpSession httpSession;
	
	/*
	@GetMapping("/")
	public String main(Model model, @LoginUser SessionUser user) {
		//SessionUser user = (SessionUser) httpSession.getAttribute("user");
		return "main/mainPage";
	}
	*/
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	// 테스트
	@GetMapping("/styleSelect")
	public String styleSelect() {
		return "itinerary/styleSelect/securityTestPage";
	}
}
