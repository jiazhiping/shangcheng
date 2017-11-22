package com.taotao.search.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.PageHelper;
import com.taotao.manager.mapper.ItemMapper;
import com.taotao.manager.pojo.Item;

public class itemData {

	private ItemMapper itemMapper;

	private CloudSolrServer cloudSolrServer;

	@Before
	public void init() {
		// 获取spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");

		// 从容器中获取需要的对象
		itemMapper = context.getBean(ItemMapper.class);
		cloudSolrServer = context.getBean(CloudSolrServer.class);

	}

	@Test
	public void testItemData() throws Exception {

		// 声明页码数和查询的结果条数
		int page = 1, pageSize = 0;
		do {
			// 设置分页
			PageHelper.startPage(page, 500);
			List<Item> list = itemMapper.select(null);

			// 把数据使用 document 进行封装
			// 声明封装容器
			List<SolrInputDocument> docs = new ArrayList<>();

			// 偏离结果查询
			for (Item item : list) {
				// 创建SolrInputDocument
				SolrInputDocument doc = new SolrInputDocument();

				// 把商品数据放到Document中
				// 商品id
				doc.addField("id", item.getId());
				// 商品item_title
				doc.addField("item_title", item.getTitle());
				// 商品item_price
				doc.addField("item_price", item.getPrice());
				// 商品item_image
				doc.addField("item_image", item.getImage());
				// 商品item_cid
				doc.addField("item_cid", item.getCid());
				// 商品item_status
				doc.addField("item_status", item.getStatus());

				// 把封装好的Document放到docs容器中
				docs.add(doc);

			}
			// 使用CloudSolrServer把数据存到索引库中
			cloudSolrServer.add(docs);
			cloudSolrServer.commit();

			// 页码+1
			page++;
			// 获取查询结果的数据条数
			pageSize = list.size();

		} while (pageSize == 500);

	}

}
