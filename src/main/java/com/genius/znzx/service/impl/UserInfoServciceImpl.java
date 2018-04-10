package com.genius.znzx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genius.znzx.common.IdGen;
import com.genius.znzx.dao.SysRoleMapper;
import com.genius.znzx.dao.UserInfoMapper;
import com.genius.znzx.entity.SysPermission;
import com.genius.znzx.entity.SysRole;
import com.genius.znzx.entity.SysUser;
import com.genius.znzx.service.UserInfoService;

/**
 * 用户信息service
 * @author fangxing.peng
 *
 */
@Service
public class UserInfoServciceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper userInfoMapper;
		
	@Override
	public SysUser findByUsername(String username) {
		SysUser param = new SysUser();
		param.setUsername(username);
		SysUser userInfo = userInfoMapper.selectOne(param);
		return userInfo;
	}

}
