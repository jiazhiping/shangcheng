package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cart")
public class CartController {

	// http://www.taotao.com/cart/738388.html?num=3
	/**
	 * 添加商品到购物车
	 * 
	 * @return
	 */
	@RequestMapping(value = "{itemId}")
	public String addItemByCart() {

		return null;

	}

}
