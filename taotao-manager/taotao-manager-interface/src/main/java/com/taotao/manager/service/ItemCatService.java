package com.taotao.manager.service;

import java.util.List;

import com.taotao.manager.pojo.ItemCat;

public interface ItemCatService extends BaseService<ItemCat>{

	
	
	/**
	 * 根据父id查询子节点
	 * @param parentId
	 * @return
	 */
	List<ItemCat> queryItemCatByParentId(Long parentId);
	
	
	/**
	 * 分页查询							
	 * @param page
	 * @param rows
	 * @return
	 */
	//public List<ItemCat> queryUserByPage(Integer page, Integer rows);

}
