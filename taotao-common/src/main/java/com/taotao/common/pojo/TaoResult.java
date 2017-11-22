package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class TaoResult<T> implements Serializable {

	// 数据结果集
	private List<T> rows;

	// 查询的数据总记录
	private Long total;

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public TaoResult(List<T> rows, Long total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public TaoResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	

}
