package com.taotao.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.service.ItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {

	/*
	 * http://127.0.0.1/rest/item/cat/{page}?row=10
	 * 
	 * @ResponseBody什么时候加
	 * 
	 * 加上@ResponseBody注解，走转换器（springMVC默认使用7个转换器），会把对象转为json数据返回，不会走视图解析器
	 * 不加@ResponseBody主机，不会走转换器，走视图解析器
	 * 加上@ResponseBody注解响应的是数据；不加 加上@ResponseBody注解，响应的是页面
	 * 
	 */
	
	/*	
	 	@PathVariable和@RequestParam，http://127.0.0.1/2?abc=1
		@PathVariable从请求url路径上获取数据2
		@RequestParam 从请求参数中获取数据（包括post表单数据），abc
		value(参数名(abc)) 取得是哪个参数, defaultValue 如果没有参数，使用这个默认值, required
		这个参数是否必须，默认值是true
	*/
	

	@Autowired
	private ItemCatService itemCatService;

	
	@RequestMapping("{page}")
	@ResponseBody
	public List<ItemCat> queryItemCatByPage(@PathVariable("page") Integer pages,
			@RequestParam(value = "row", defaultValue = "10") Integer rows) {
		// List<ItemCat> list = this.itemCatService.queryUserByPage(pages,
		// rows);
		List<ItemCat> list = this.itemCatService.queryByPage(pages, rows);
		return list;

	}
	
	
	/**
	 * 查询所有父节点
	 * @param parentId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<ItemCat> queryItemCatByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<ItemCat> list = itemCatService.queryItemCatByParentId(parentId);

		return list;
	}

}
