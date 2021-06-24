package com.JejuDojang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Controller
@Log
public class designTestController {

	
	@GetMapping("/")
	public String indexPage(){
		return "index";
	}
	
	@GetMapping("/index")
	public String indexPage2(){
		return "index";
	}
	
	
 


}