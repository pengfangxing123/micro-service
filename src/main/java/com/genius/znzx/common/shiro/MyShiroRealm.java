package com.genius.znzx.common.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.genius.znzx.entity.SysPermission;
import com.genius.znzx.entity.SysRole;
import com.genius.znzx.entity.SysUser;
import com.genius.znzx.service.UserInfoService;

public class MyShiroRealm extends AuthorizingRealm {
	private static Logger logger = Logger.getLogger(MyShiroRealm.class);
	
	@Resource
	private UserInfoService userInfoService;

	/**
	 * 权限控制
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUser userInfo  = (SysUser)principals.getPrimaryPrincipal();
		List<SysRole> roleList = userInfo.getRoleList();
		roleList.forEach(p->{
			authorizationInfo.addRole(p.getRole());
			List<SysPermission> permissions = p.getPermissions();
			permissions.forEach(x->{
				authorizationInfo.addStringPermission(x.getPermission());
			});
		});
		return authorizationInfo;
	}

	/**
	 * 登陆验证
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("MyShiroRealm.doGetAuthenticationInfo()");
		String username = (String)token.getPrincipal();
		logger.info("username="+username);
		
		//通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser userInfo = userInfoService.findByUsername(username);
        if(userInfo == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
		return authenticationInfo;
	}

}
