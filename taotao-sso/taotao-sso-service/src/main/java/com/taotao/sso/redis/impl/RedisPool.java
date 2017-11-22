package com.taotao.sso.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.sso.redis.RedisUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPool implements RedisUtils {

	@Autowired
	private JedisPool JedisPool;

	@Override
	public void set(String key, String value) {
		// 从连接池获取连接对象
		Jedis jedis = JedisPool.getResource();
		// 使用连接对象进行业务操作
		jedis.set(key, value);
		// 关闭资源
		jedis.close();

	}

	@Override
	public void set(String key, String value, Integer seconds) {
		// 从连接池获取连接对象
		Jedis jedis = JedisPool.getResource();
		// 使用链接对象进行业务操作
		jedis.set(key, value);
		jedis.expire(key, seconds);
		// 关闭资源
		jedis.close();

	}

	@Override
	public String get(String key) {
		// 从连接池获取连接对象
		Jedis jedis = JedisPool.getResource();
		// 使用链接对象进行业务操作
		String result = jedis.get(key);
		// 关闭资源
		jedis.close();
		return result;
	}

	@Override
	public void del(String key) {
		// 从连接池获取连接对象
		Jedis jedis = JedisPool.getResource();
		// 使用链接对象进行业务操作
		jedis.del(key);
		// 关闭资源
		jedis.close();

	}

	@Override
	public void expire(String key, Integer seconds) {
		// 从连接池获取连接对象
		Jedis jedis = JedisPool.getResource();
		// 使用连接对象进行业务操作
		jedis.expire(key, seconds);
		// 关闭资源
		jedis.close();
	}

	@Override
	public long incr(String key) {
		// 从连接池获取连接对象
		Jedis jedis = JedisPool.getResource();
		// 使用连接对象进行业务操作
		Long count = jedis.incr(key);
		// 关闭资源
		jedis.close();
		return count;
	}

}
