package com.taotao.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.taotao.search.service.SearchService;

public class ItemMessageListener implements MessageListener {

	@Autowired
	private SearchService searchService;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void onMessage(Message message) {
		// 判断消息类型是否为 TextMessage
		if (message instanceof TextMessage) {
			// 是 强转
			TextMessage textMessage = (TextMessage) message;
			try {
				// 获取消息内容
				String json = textMessage.getText();
				// 判断json数据不为空
				if (StringUtils.isNotBlank(json)) {
					// 解析json数据获取jsonNode
					JsonNode jsonNode = MAPPER.readTree(json);
					// 获取操作符
					String type = jsonNode.get("type").asText();
					// 获取商品id
					long itemId = jsonNode.get("itemId").asLong();

					// 根据消息内容 进行操作
					if ("save".equals(type)) {
						// 如果是save操作符，就执行新增数据到索引库
						this.searchService.addItem(itemId);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
