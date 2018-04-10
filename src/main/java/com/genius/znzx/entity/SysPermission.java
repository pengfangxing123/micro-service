package com.genius.znzx.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Id;

/**
 * 权限实体
 * @author fangxing.peng
 *
 */
@Table(name="SYS_PERMISSION")
public class SysPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5670781399992867439L;
	@Id
	private String id;//主键
	@Column(name="NAME")
	private String name;//名称
	@Column(name="RESOURCE_TYPE")
	private Integer resourceType;//资源类型，[menu|button]
	@Column(name="URL")
	private String url;//资源路径
	@Column(name="PERMISSION")
	private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	@Column(name="PARENT_ID")
	private String parentId; //父编号
	@Column(name="AVAILABLE")
	private Integer available;//是否可用
	@Transient
	private List<SysRole> roles;//角色列表
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getResourceType() {
		return resourceType;
	}
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

}
