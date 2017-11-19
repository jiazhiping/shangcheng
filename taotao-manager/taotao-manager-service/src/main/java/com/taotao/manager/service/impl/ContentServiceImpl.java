package com.taotao.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Content;
import com.taotao.manager.redis.RedisUtils;
import com.taotao.manager.service.ContentService;

@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public TaoResult<Content> queryContentByPage(Integer page, Integer rows, Long categoryId) {
		// 设置分页参数
		PageHelper.startPage(page, rows);

		// 设置查询条件
		Content content = new Content();
		content.setCategoryId(categoryId);

		// 执行查询
		List<Content> list = super.queryListByWhere(content);

		// 封装返回对象
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		TaoResult<Content> taoResult = new TaoResult<>(list, pageInfo.getTotal());

		return taoResult;
	}

	@Autowired
	private RedisUtils redisUtils;
	@Value("${TAOTAO_PORTAL_AD}")
	private String TAOTAO_PORTAL_AD;

	@Override
	public String queryContentByCategoryId(Long categoryId) {

		// 从缓存中命中
		try {
			String redisJson = redisUtils.get(TAOTAO_PORTAL_AD);
			if (StringUtils.isNotBlank(redisJson)) {
				// 命中 返回
				return redisJson;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 没有命中执行原有逻辑，从MySQL中查询
		// 根据分类id 查询内容
		// 设置查询条件
		Content content = new Content();
		content.setCategoryId(categoryId);

		// 执行查询
		List<Content> list = super.queryListByWhere(content);
		// 声明List<Map>容器
		ArrayList<Map<String, Object>> results = new ArrayList<>();
		// 遍历查询的内容，封装到List<map>中
		for (Content c : list) {
			// 声明map
			Map<String, Object> map = new HashMap<>();

			// 根据前台页面数据格式，进行封装
			map.put("srcB", c.getPic());
			map.put("height", 240);
			map.put("alt", "");
			map.put("width", 670);
			map.put("src", c.getPic());
			map.put("widthB", 550);
			map.put("href", c.getUrl());
			map.put("heightB", 240);

			// 把封装后的map放到results容器中
			results.add(map);

		}

		// 把封装的结果转为json格式的数据
		String json = "";

		try {
			json = MAPPER.writeValueAsString(results);
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		
		// 把数据保存在redis中
		try {
			redisUtils.set(TAOTAO_PORTAL_AD, json, 60*60*24);
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		return json;

	}

}
