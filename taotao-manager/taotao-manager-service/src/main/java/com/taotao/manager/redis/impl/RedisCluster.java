package com.taotao.manager.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.manager.redis.RedisUtils;

import redis.clients.jedis.JedisCluster;

public class RedisCluster implements RedisUtils{
	
	@Autowired
	private JedisCluster jedisCluster;
	

	@Override
	public void set(String key, String value) {
		jedisCluster.set(key, value);
		
	}

	@Override
	public void set(String key, String value, Integer seconds) {
		jedisCluster.set(key, value);
		jedisCluster.expire(key, seconds);
		
	}

	@Override
	public String get(String key) {
		String result = jedisCluster.get(key);
		return result;
	}

	@Override
	public void del(String key) {
		jedisCluster.del(key);
		
	}

	@Override
	public void expire(String key, Integer seconds) {
		jedisCluster.expire(key, seconds);
		
	}

	@Override
	public long incr(String key) {
		Long count = jedisCluster.incr(key);
		return count;
	}

}
