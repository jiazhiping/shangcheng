package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

	//通用页面跳转
	// http://www.taotao.com/page/register.html
	// http://www.taotao.com/page/login.html
	
	
	// @ResponseBody 不能加这个注解，加了这个注解就不走视图解析器
	@RequestMapping("{pageName}")
	public String page(@PathVariable String pageName){
		
		return pageName;
		
	}
}
