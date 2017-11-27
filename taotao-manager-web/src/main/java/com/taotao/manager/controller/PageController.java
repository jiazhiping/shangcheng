package com.taotao.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.service.ItemCatService;

@Controller
@RequestMapping("page")
class PageController {

	@RequestMapping("{pageName}")
	public String toPage(@PathVariable("pageName") String pageName){
		
		return pageName;
		
	}
//http://127.0.0.1:8080/rest/page/index
}
