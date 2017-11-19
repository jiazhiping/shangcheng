package com.taotao.manager.service;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;

public interface ItemService extends BaseService<Item>{

	
	/**
	 * 添加商品
	 */
	void saveItem(Item item, String desc);
	
	
	/**
	 * 商品分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	TaoResult<Item> queryItemByPage(Integer page, Integer rows);

	

	
}
