package com.bjbj.sso.service;

import com.bjbj.sso.pojo.User;

public interface UserService {

	// 校验数据是否存在
	Boolean findCheckUser(String param, Integer type);
	// 用户注册
	String saveUser(User user);
	// 用户登录
	String findUserByUP(String username, String password);
	String findUserByTicket(String ticket);
	// 删除ticket
	void deleteTicket(String ticket);

}
