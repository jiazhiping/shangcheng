package com.taotao.cart.redis;

public interface RedisUtils {

	/**
	 * 保存数据
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value);

	/**
	 * 保存数据的同时设置有效时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void set(String key, String value, Integer seconds);

	/**
	 * 查询数据
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * 删除数据
	 * 
	 * @param key
	 */
	public void del(String key);

	/**
	 * 根据key设置有效时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public void expire(String key, Integer seconds);

	/**
	 * incr方法，作用是每次加一
	 * 
	 * @param key
	 * @return
	 */
	public long incr(String key);
}
