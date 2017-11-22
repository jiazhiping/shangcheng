package com.taotao.search.service;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;

public interface SearchService {

	/**
	 * 从solr索引库中查询商品数据
	 * 
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 */
	TaoResult<Item> search(String q, Integer page, Integer rows);

	/**
	 * 根据商品id保存数据到索引库
	 * 
	 * @param itemId
	 */
	void addItem(Long itemId);

}
