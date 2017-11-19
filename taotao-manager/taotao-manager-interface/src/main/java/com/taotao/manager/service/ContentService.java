package com.taotao.manager.service;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Content;

public interface ContentService extends BaseService<Content> {

	/**
	 * 根据分类Id 分页查询
	 * 
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	TaoResult<Content> queryContentByPage(Integer page, Integer rows, Long categoryId);

	/**
	 * 根据分类id查询内容
	 * 
	 * @param categoryId
	 * @return 
	 */
	String queryContentByCategoryId(Long categoryId);

}
