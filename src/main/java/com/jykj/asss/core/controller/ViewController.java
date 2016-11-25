package com.jykj.asss.core.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转controller
 * ***/
@Controller
public class ViewController {
	
	/**页面跳转*/
	@RequestMapping("/view/{jspname}")
	public String view(@PathVariable("jspname") String jspname){
		String targetUrl = "/jsp/" + jspname.replaceAll("\\.", "/");
        return targetUrl;
	}
	
	/**登陆页*/
	@RequestMapping(value = "/login")
    public ModelAndView toLogin() {
        return new ModelAndView("/login");
    }
}
