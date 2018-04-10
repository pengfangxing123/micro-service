package com.genius.znzx.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Id;

/**
 * 角色实体 
 * @author fangxing.peng
 *
 */
@Table(name="SYS_ROLE")
public class SysRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2749041969305104008L;

	@Id
	private String id; // id
	@Column(name="ROLE")
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
	@Column(name="DESCRIPTION")
    private String description; // 角色描述,UI界面显示使用
	@Column(name="AVAILABLE")
    private Integer available; // 是否可用,如果不可用将不会添加给用户
    @Transient
    private List<SysPermission> permissions;//角色 -- 权限关系
    @Transient
    private List<SysUser> userInfos;// 一个角色对应多个用户
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public List<SysPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}
	public List<SysUser> getUserInfos() {
		return userInfos;
	}
	public void setUserInfos(List<SysUser> userInfos) {
		this.userInfos = userInfos;
	}
    
    
}
