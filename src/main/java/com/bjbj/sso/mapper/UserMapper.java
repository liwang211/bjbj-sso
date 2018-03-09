package com.bjbj.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.bjbj.common.mapper.SysMapper;
import com.bjbj.sso.pojo.User;

public interface UserMapper extends SysMapper<User> {

	int findCheckUser(@Param("param") String param, @Param("cloumn") String cloumn);

}
