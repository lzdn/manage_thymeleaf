package com.lzdn.manage.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("index")
	public String toIndex(HttpServletRequest request, HttpServletResponse response){
		return "admin/index";
	}
	
	@RequestMapping("home")
	public String toHome(HttpServletRequest request, HttpServletResponse response){
		return "admin/home";
	}
	
	@RequestMapping("test")
	public String test(HttpServletRequest request, HttpServletResponse response){
		return "admin/test";
	}
}
