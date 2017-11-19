package com.taotao.manager.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manager.mapper.TestMapper;
import com.taotao.manager.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestMapper testMapper;
	

	@Override
	public String queryDate() {
		String date = testMapper.queryDate();
		return date;
	}

}
