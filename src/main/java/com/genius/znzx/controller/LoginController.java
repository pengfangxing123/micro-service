package com.genius.znzx.controller;



import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.genius.znzx.common.CookieUtil;
import com.genius.znzx.common.IdGen;
import com.genius.znzx.common.JsonResult;




@Controller
public class LoginController {
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	private static final int SESSION_LIMIT_TIME = 1800;//session到期时间(s)
	private static final String COOKIE_NAME ="xt_token";//用户登录COOKIE名称
	
    @RequestMapping("toLogin")
    public String index(){
        return"/login";
    }
    
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult UserLogin(@RequestParam("username") String username,@RequestParam("password")String password,
    		HttpServletResponse response){	
    	logger.info("用户登陆usernam="+username);
    	Subject currentUser = SecurityUtils.getSubject();
    	UsernamePasswordToken token = new UsernamePasswordToken(username,password);
    	String msg="";
        try {  
            currentUser.login(token); 
            msg = "{success:true,message:'登陆成功'}"; 
//            Session session = currentUser.getSession();
//            String token_ = CookieUtil.setCookie(response,COOKIE_NAME,IdGen.uuid(),SESSION_LIMIT_TIME);
//            session.setAttribute(token_, user);
//            session.setMaxInactiveInterval(SESSION_LIMIT_TIME);
            return JsonResult.build(0, msg);
        } catch (UnknownAccountException ex) {  
        	msg = "{success:false,message:'账号错误'}";  
        	return JsonResult.build(-1, msg);
        } catch (IncorrectCredentialsException ex) {  
        	msg = "{success:false,message:'密码错误'}";  
        	return JsonResult.build(-2, msg);
        } catch (LockedAccountException ex) {  
        	msg = "{success:false,message:'账号已被锁定，请与管理员联系'}";  
        	return JsonResult.build(-3, msg);
        } catch (AuthenticationException ex) {  
        	msg = "{success:false,message:'您没有授权'}";  
        	return JsonResult.build(-4, msg);
        } 	       	
    }
    
    @RequestMapping("/403")
    public String unauthorizedRole(){
    	logger.info("------没有权限-------");
    	return "403";
    }
    
}
