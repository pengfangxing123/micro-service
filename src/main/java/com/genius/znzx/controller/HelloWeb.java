package com.genius.znzx.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RestController 返回值是string 时不能用这个 他会直接返回json 而不是跳转页面 =@Controller+@ResponseBody
@RequestMapping("/hello")
public class HelloWeb {
	//测试jsp需要打开embed依赖
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView getIndex(){
		ModelAndView model = new ModelAndView("index");
		return model;
	} 
	//测试thymeleaf模板
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public ModelAndView testThy(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("name","pfx");
		model.addObject("hello", "hello thymeleaf");
		return model;
	}
	//测试thymeleaf模板 
	@RequestMapping("/index1.do")
	public String testThy1(Model model){
		model.addAttribute("name","pfx");
		model.addAttribute("hello", "hello thymeleaf");
		return "index";
	} 
}
