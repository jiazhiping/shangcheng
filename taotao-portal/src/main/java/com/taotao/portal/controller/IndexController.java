package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manager.service.ContentService;

@Controller
@RequestMapping("index")
public class IndexController {
	
	@Autowired
	private ContentService contentService;

	@Value("${TAOTAO_AD_ID}")
	private Long categoryId;
	
	// http://127.0.0.1:8085/index.html
	/**
	 * 门户首页展示
	 * @return
	 */
	@RequestMapping
	public String index(Model model) {
		//查询轮播广告数据
		String AD = contentService.queryContentByCategoryId(categoryId);
		
		model.addAttribute("AD", AD);
		
		return "index";
	}

}
