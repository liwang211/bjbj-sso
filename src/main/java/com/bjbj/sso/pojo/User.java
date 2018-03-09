package com.bjbj.sso.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.bjbj.common.po.BasePojo;

@Table(name = "tb_user")
@JsonIgnoreProperties(ignoreUnknown=true)
public class User extends BasePojo{

	
	private static final long serialVersionUID = -3912926107505926788L;
	@Id							// 表示主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 主键自增
	private Long id; 			// 用户Id
	private String username; 	// 用户名
	private String password; 	// 密码
	private String phone; 		// 电话
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone + "]";
	}
	
	

}
