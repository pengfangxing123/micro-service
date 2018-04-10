package com.genius.znzx.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Id;

/**
 * 用户实体
 * @author fangxing.peng
 *
 */
@Table(name="SYS_USER")
public class SysUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -801122615986354671L;
	@Id
	private String id;
	@Column(name="USER_NAME")
	private String username;//帐号
	@Column(name="NAME")
	private String name;//用户名称
	@Column(name="PASSWORD")
	private String password;
	@Column(name="SALT")
	private String salt;//加密密码的盐
	@Column(name="STATUS")
	private Integer status;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
	@Transient
	private List<SysRole> roleList;// 一个用户具有多个角色
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<SysRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}
	
    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解

}
