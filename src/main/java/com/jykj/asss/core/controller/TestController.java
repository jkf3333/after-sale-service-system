package com.jykj.asss.core.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 测试controller
 * ***/
@Controller
@RequestMapping("/test")
public class TestController {
	
	public String sayHi(){
        return "/jsp/test.jsp";
	}
	
	@RequestMapping(value = "/hello")
    public ModelAndView toLogin() {
        return new ModelAndView("/index.jsp");
    }
}
