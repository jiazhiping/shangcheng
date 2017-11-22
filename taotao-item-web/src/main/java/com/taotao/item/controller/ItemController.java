package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {
//http://item.taotao.com/item/1474391930.html
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService descService;
	
	@RequestMapping("{itemId}")
	public String item(Model model,@PathVariable Long itemId){
		//查询商品  基础数据
		Item item = itemService.queryById(itemId);
		//查询商品 描述信息
		ItemDesc itemDesc = descService.queryById(itemId);
		
		
		//放入model  返回前台
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		
		return "item";
		
		
	}
}
