package com.bjbj.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjbj.common.vo.SysResult;
import com.bjbj.sso.pojo.User;
import com.bjbj.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkUser(@PathVariable String param, @PathVariable Integer type, String callback) {

		try {
			// 根据传递的参数判断数据是否存在
			Boolean result = userService.findCheckUser(param, type);
			MappingJacksonValue jacksonValue = new MappingJacksonValue(SysResult.oK(result));
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user) {

		try {
			String username = userService.saveUser(user);
			return SysResult.oK(username);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "用户新增失败");
		}
	}

	@RequestMapping("/login")
	@ResponseBody
	public SysResult doLogin(@RequestParam("u") String username, @RequestParam("p") String password) {

		try {

			String ticket = userService.findUserByUP(username, password);

			return SysResult.oK(ticket);

		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "登录失败");
		}
	}

	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public Object findUserByTicket(@PathVariable String ticket, String callback) {

		// 获取用户JSON数据
		String userJSON = userService.findUserByTicket(ticket);

		MappingJacksonValue jacksonValue = new MappingJacksonValue(SysResult.oK(userJSON));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	@RequestMapping("/logout/{ticket}")
	public SysResult doLogout(@PathVariable String ticket) {
		
		userService.deleteTicket(ticket);
		
		return SysResult.oK();
	}
	

}
