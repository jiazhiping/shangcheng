package com.taotao.manager.service;

import java.util.List;

public interface BaseService<T> {

	/**
	 * 根据id查询数据
	 * 
	 * @param id
	 * @return
	 */
	public T queryById(Long id);

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public List<T> queryAll();

	/**
	 * 
	 * 根据条件查询数据条数
	 * 
	 * @param t
	 * @return
	 */
	public int queryCountByWhere(T t);

	/**
	 * 根据条件查询结果集
	 */
	public List<T> queryListByWhere(T t);

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<T> queryByPage(Integer page, Integer rows);

	/**根据条件查询一条
	 * @param t
	 * @return
	 */
	public T queryOne(T t);
	
	/**新增 不忽略空参数
	 * @param t
	 */
	public void save(T t);
	
	/**新增 忽略空参数
	 * @param t
	 */
	public void saveSelective(T t);
	
	
	/**更新   不忽略星空参数
	 * @param t
	 */
	public void updateById(T t);
	
	
	/**更新  忽略空参数
	 * @param t
	 */
	public void updateByIdSelective(T t);
	
	/**根据id删除
	 * @param id
	 */
	public void deleteById(Long id);	
	
	/**
	 * 根据id批量删除
	 * @param ids
	 */
	public void deleteByIds(List<Object> ids);
	
}
