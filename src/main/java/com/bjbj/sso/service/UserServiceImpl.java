package com.bjbj.sso.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bjbj.sso.mapper.UserMapper;
import com.bjbj.sso.pojo.User;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JedisCluster jedisCluster;

	// @Value("JT_TICKET_KEY")
	// private String JT_TICKET_KEY;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Boolean findCheckUser(String param, Integer type) {

		String cloumn = null;

		switch (type) {
		case 1:
			cloumn = "username";
			break;
		case 2:
			cloumn = "phone";
			break;
		case 3:
			cloumn = "email";
			break;

		}

		int count = userMapper.findCheckUser(param, cloumn);
		// if (count < 1)
		// return true;
		// else
		// return false;
		return count == 1 ? true : false;
	}

	@Override
	public String saveUser(User user) {

		// 将数据准备完整
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		// 将密码进行加密处理
		String md5Password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Password);
		// 修改bug 填补邮箱为null的情况
		user.setEmail(user.getPhone());
		// 执行insert操作
		userMapper.insert(user);

		return user.getUsername();
	}

	@Override
	public String findUserByUP(String username, String password) {

		// 封装数据
		User user = new User();
		user.setUsername(username);
		// 密码加密
		String md5Password = DigestUtils.md5Hex(password);
		user.setPassword(md5Password);
		try {
			// 查询数据库
			user = userMapper.select(user).get(0);
			// 获取ticket
			String md5ticket = DigestUtils.md5Hex("JT_TICKET_" + System.currentTimeMillis() + user.getUsername());

			String userJSON = objectMapper.writeValueAsString(user);

			// // TODO
			// System.out.println(user.getId());
			// 将ticket存入redis缓存
			jedisCluster.set(md5ticket, userJSON);
			return md5ticket;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("登录失败");
		}
	}

	@Override
	public String findUserByTicket(String ticket) {
		
		return jedisCluster.get(ticket);

	}

	@Override
	public void deleteTicket(String ticket) {

		
		jedisCluster.del(ticket);
		System.out.println("结果 :" + jedisCluster.get(ticket));
	}
}
