package com.taotao.manager.service;

import java.util.List;

import com.taotao.manager.pojo.ContentCategory;

public interface ContentCategoryService extends BaseService<ContentCategory> {

	/**
	 * 根据父id查询内容分类
	 * 
	 * @param parentId
	 * @return
	 */
	List<ContentCategory> queryContentCategoryByParentId(Long parentId);

	/**
	 * 保存内容分类
	 * 
	 * @param contentCategory
	 * @return
	 */
	ContentCategory saveContentCategory(ContentCategory contentCategory);

	/**
	 * 删除内容分类
	 * 
	 * @param parentId
	 * @param id
	 */
	void deleteContentCategory(Long parentId, Long id);

}
