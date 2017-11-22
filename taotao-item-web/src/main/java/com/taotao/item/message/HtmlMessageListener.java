package com.taotao.item.message;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.management.j2ee.statistics.Statistic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HtmlMessageListener implements MessageListener {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void onMessage(Message message) {
		// 判断小心得类型是否为 TextMessage
		if (message instanceof TextMessage) {
			// 如果是 就强转
			TextMessage textMessage = (TextMessage) message;

			try {
				// 获取消息的内容
				String json = textMessage.getText();

				// 判断小心是否为空
				if (StringUtils.isNotBlank(json)) {
					// 解析json数据
					JsonNode jsonNode = MAPPER.readTree(json);

					// 获取操作符
					String type = jsonNode.get("type").asText();

					// 获取商品id
					Long itemId = jsonNode.get("itemId").asLong();

					// 根据消息生成惊天页面
					if ("save".equals(type)) {
						// 新增 生成
						genHtml(itemId);

					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 生成静态页面
	 * 
	 * @param itemId
	 */

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;

	private void genHtml(Long itemId) throws Exception {
		// 获取 freemarker 核心对象
		Configuration cfg = freeMarkerConfigurer.getConfiguration();

		// 使用 核心对象 , 获取模板 参数是模板的名字
		Template template = cfg.getTemplate("item.ftl");

		// 声明模板数据
		Map<String, Object> root = new HashMap<>();

		// 使用商品服务 根据id 查询 商品数据
		Item item = itemService.queryById(itemId);
		ItemDesc itemDesc = itemDescService.queryById(itemId);

		// 把商品基础数据放到模型数据root中
		root.put("item", item);
		// 把商品描述数据放到模型数据root中
		root.put("itemDesc", itemDesc);

		// 生命输出对象
		Writer out = new FileWriter(new File("E:/html/item/" + itemId + ".html"));

		// 使用模板输出静态页面，需要两个参数，一个是模型数据，一个是输出对象Writer
		template.process(root, out);

	}

}
