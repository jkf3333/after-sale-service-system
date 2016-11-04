package com.jykj.asss.core.controller;


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
        return new ModelAndView("/jsp/login");
    }
	/**首页*/
	@RequestMapping(value = "/index")
    public ModelAndView toIndex() {
        return new ModelAndView("/index");
    }
}
