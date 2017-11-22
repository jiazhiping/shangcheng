package com.taotao.portal.controller;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.TTCCLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.User;
import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@Value("${TT_TICKET}")
	private String TT_TICKET;

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "doRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doRegister(User user) {

		// 单点登陆服务 实现用户注册
		userService.doRegister(user);

		// 封装返回数据
		Map<String, Object> map = new HashMap<>();
		map.put("status", "200");

		return map;

	}

	// type: "POST",
	// url: "/service/user/doLogin?r=" + Math.random(),
	// data: {username:_username,password:_password},
	// dataType : "json",
	// if (obj.status == 200)

	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(HttpServletRequest request, HttpServletResponse response, User user) {
		// 嗲用单点登录 用户登陆 返回 ticket
		String ticket = userService.doLogin(user);

		// 声明map
		Map<String, Object> map = new HashMap<>();

		// 判断ticket不为空
		if (StringUtils.isNotBlank(ticket)) {
			// 登陆成功 返回ticket，把ticket放到cookie中
			CookieUtils.setCookie(request, response, TT_TICKET, ticket, 60 * 60 * 24, true);

			// 设置状态吗 200
			map.put("status", "200");
		}
		// 如果登录失败，不对返回对象map赋值
		return map;

	}
}
