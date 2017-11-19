package com.taotao.manager.service.impl;

import java.awt.ItemSelectable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Supplier;
import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	@Autowired
	private ItemDescService itemDescService;

	/**
	 * 添加商品
	 */
	@Override
	public void saveItem(Item item, String desc) {
		// 保存商品
		item.setStatus(1);
		super.save(item);

		// 保存商品描述
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDescService.save(itemDesc);

		// 新增商品 发消息给mq
		// 消息内容是：操作符:type:save 。商品数据：itemId:123
		sendMQ("save", item.getId());

	}

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Destination destination;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 发消息给mq
	private void sendMQ(final String type, final Long itemId) {
		jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// 创建TextMessage消息体
				ActiveMQTextMessage textMessage = new ActiveMQTextMessage();

				// 创建消息内容 使用json格式的数据
				// 创建封装消息的map
				Map<String, Object> map = new HashMap<>();

				// 设置消息
				// 操作符
				map.put("type", type);
				// 商品id
				map.put("itemId", itemId);

				try {
					// 把消息转为jso格式的数据
					String json = MAPPER.writeValueAsString(map);

					// 设置消息
					textMessage.setText(json);
					System.out.println("消息发送成功,内容是:" + json);

				} catch (Exception e) {
					e.printStackTrace();
				}

				return textMessage;
			}
		});

	}

	/**
	 * 商品分页查询
	 */
	@Override
	public TaoResult<Item> queryItemByPage(Integer page, Integer rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);
		// 设置查询条件
		Item item = new Item();
		// 设置查询正常的商品
		item.setStatus(1);

		// 执行查询
		List<Item> list = super.queryListByWhere(item);

		// 获取分页的详细数据
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		TaoResult<Item> taoResult = new TaoResult<>(list, pageInfo.getTotal());

		return taoResult;
	}

}
