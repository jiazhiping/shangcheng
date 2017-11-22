package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.mapper.ItemMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private CloudSolrServer cloudSolrServer;

	@Override
	public TaoResult<Item> search(String q, Integer page, Integer rows) {

		// 声明返回结果
		TaoResult<Item> taoResult = new TaoResult<>();

		// 创建搜索对象
		SolrQuery solrQuery = new SolrQuery();

		// 判断搜索关键字是否为空
		if (StringUtils.isNotBlank(q)) {
			// 设置查询语句
			solrQuery.setQuery("item_title:" + q + " AND item_status:1");
		} else {
			// 如果为空 查询所有数据
			solrQuery.setQuery("item_status:1");
		}

		// 设置分页
		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);

		// 设置高粱
		// 开启高粱
		solrQuery.setHighlight(true);
		// 设置高粱字段
		solrQuery.addHighlightField("item_title");
		// 设置前缀
		solrQuery.setHighlightSimplePre("<font color='red'>");
		// 设置后缀
		solrQuery.setHighlightSimplePost("</font>");

		try {
			// 使用CloudSolrServer实现查询，返回response
			QueryResponse response = cloudSolrServer.query(solrQuery);

			// 获得高粱数据
			Map<String, Map<String, List<String>>> map = response.getHighlighting();

			// 解析 response结果集 获取results
			SolrDocumentList results = response.getResults();

			// 声明存放商品的容器
			List<Item> list = new ArrayList<>();

			// 遍历results，封装返回结果，商品
			for (SolrDocument solrDocument : results) {
				// 解析高亮数据
				List<String> hlist = map.get(solrDocument.get("id").toString()).get("item_title");

				// 创建商品对象
				Item item = new Item();

				// 设置商品数据
				// 商品id
				item.setId(Long.parseLong(solrDocument.get("id").toString()));
				// 商品item_title
				// 判断是否有高亮数据
				if (hlist != null && hlist.size() > 0) {
					item.setTitle(hlist.get(0));
				} else {
					// 没有高亮数据设置非高亮的标题
					item.setTitle(solrDocument.get("item_title").toString());
				}
				// 商品item_price
				item.setPrice(Long.parseLong(solrDocument.get("item_price").toString()));
				// 商品item_image
				item.setImage(solrDocument.get("item_image").toString());
				// 商品item_cid
				item.setCid(Long.parseLong(solrDocument.get("item_cid").toString()));
				// 商品item_status,不能获取，否则会空指针异常，因为之前的设置是不存储的
				// 封装好的商品放到商品的list容器中
				list.add(item);

			}
			// 封装返回对象taoResult
			taoResult.setRows(list);
			taoResult.setTotal(results.getNumFound());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 异常 不能返回null，应该返回空对象
		return taoResult;
	}

	@Autowired
	private ItemMapper itemMapper;

	/**
	 * 添加商品 同步更新
	 */
	@Override
	public void addItem(Long itemId) {
		// 根据商品id查询商品数据
		Item item = itemMapper.selectByPrimaryKey(itemId);

		// 声明 商品数据用SolrInputDocument进行封装
		SolrInputDocument doc = new SolrInputDocument();

		// 赋值
		// id
		doc.addField("id", item.getId());
		// item_title
		doc.addField("item_title", item.getTitle());
		// item_price
		doc.addField("item_price", item.getPrice());
		// item_image
		doc.addField("item_image", item.getImage());
		// item_cid
		doc.addField("item_cid", item.getCid());
		// item_status
		doc.addField("item_status", item.getStatus());

		// 把商品信息保存到索引哭中
		try {
			cloudSolrServer.add(doc);
			cloudSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
