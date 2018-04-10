package com.genius.znzx.service;

import com.genius.znzx.entity.SysUser;

public interface UserInfoService {

	/**
	 * 根据用户名称查找用户
	 * @param username 用户名
	 * @return
	 */
	public SysUser findByUsername(String username);

}
