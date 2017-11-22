package com.taotao.sso.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.taotao.manager.mapper.UserMapper;
import com.taotao.manager.pojo.User;
import com.taotao.sso.redis.RedisUtils;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean check(String param, Integer type) {
		// 声明查询条件
		User user = new User();
		switch (type) {
		case 1:
			user.setUsername(param);
			break;
		case 2:
			user.setPhone(param);
			break;
		case 3:
			user.setEmail(param);
			break;

		default:
			break;
		}

		// 执行查询
		int count = userMapper.selectCount(user);
		return count == 0;
	}

	@Autowired
	private RedisUtils redisUtils;

	@Value("{TAOTAO_TICKET_KEY}")
	private String TAOTAO_TICKET_KEY;

	@Value("${TAOTAO_TICKET_INCR}")
	private String TAOTAO_TICKET_INCR;

	// json
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public User queryUserByTicket(String ticket) {
		// 根据ticket从redis中查询 用户数据 返回json数据
		// 添加前缀,便于管理
		String json = redisUtils.get(TAOTAO_TICKET_KEY + ticket);

		// 判断json数据不为空
		if (StringUtils.isNotBlank(json)) {
			try {
				// 把json 转化 user
				User user = MAPPER.readValue(json, User.class);

				// 查询成功需要重置登录的有效时间,1小时
				redisUtils.expire(TAOTAO_TICKET_KEY + ticket, 60 * 60);

				// 返回结果
				return user;

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 如果查询到的数据为空，或者转换异常，返回null
		return null;
	}

	@Override
	public void doRegister(User user) {
		// 设置用户数据
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());

		// 给用户密码加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));

		// 保存到数据库
		userMapper.insert(user);

	}

	@Override
	public String doLogin(User user) {
		// 把 密码 加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		// 根据条件查询用户
		User result = userMapper.selectOne(user);

		// 判断查询结果
		if (result != null) {
			// 如果不为空表示用户已登录
			// 生成唯一数：使用redis单线程的特点，加上incr生成唯一数
			// 生成唯一的key，就是ticket：使用唯一数+用户id
			String ticket = "" + redisUtils.incr(TAOTAO_TICKET_INCR) + result.getId();

			try {
				// 将用户信息保存到 redis
				redisUtils.set(TAOTAO_TICKET_KEY + ticket, MAPPER.writeValueAsString(result), 60 * 60);
				
				//返回 ticket
				return ticket;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		// 如果登录失败或者有异常，返回null
		return null;
	}

}
