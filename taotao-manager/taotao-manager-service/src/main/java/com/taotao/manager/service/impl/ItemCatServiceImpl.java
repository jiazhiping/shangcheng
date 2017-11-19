package com.taotao.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.github.pagehelper.PageHelper;
import com.taotao.manager.mapper.ItemCatMapper;
import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.service.ItemCatService;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

	
	/**
	 * 父id查子节点
	 */
	@Override
	public List<ItemCat> queryItemCatByParentId(Long parentId) {
		// 设置查询的参数
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		
		//查询
		List<ItemCat> list = super.queryListByWhere(itemCat);
		
		return list;
	}

	/*@Resource
	private ItemCatMapper itemCatMapper;

	@Override
	public List<ItemCat> queryUserByPage(Integer page, Integer rows) {
		// 设置分页参数，第一个参数是当前页数，第二个参数每页显示数据条数
		PageHelper.startPage(page, rows);

		// 执行查询
		List<ItemCat> list = itemCatMapper.select(null);

		return list;
	}*/
	
	
	

}
