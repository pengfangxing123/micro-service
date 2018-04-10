package com.genius.znzx.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * cookie工具类
 * @author hongtao.he
 *
 */
public class CookieUtil {

	/**
	 * 设置token信息
	 * 
	 * @param response
	 * @param cookieName cookie名称
	 * @param tokenValue token内容
	 * @param time token到期时间
	 * @return
	 */
	 public static String setCookie(HttpServletResponse response,String cookieName,String tokenValue,int time) {
			Cookie cookie = new Cookie(cookieName,tokenValue);
			cookie.setMaxAge(time);
			cookie.setPath("/");
			response.addCookie(cookie);
			return tokenValue;
	}
 
	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param cookieName cookie名称
	 * @return
	 */
	 public static String delCookie(HttpServletRequest request,HttpServletResponse response,String cookieName){  
		 String token ="";
         Cookie[] cookies = request.getCookies();  
         if (null!=cookies){
             for(Cookie cookie : cookies){  
                 if(cookie.getName().equals(cookieName)){  
                     cookie.setValue(null);  
                     cookie.setPath("/");  
                     cookie.setMaxAge(0);
                     response.addCookie(cookie);
                     break;  
                 }  
             }  
         } 
         return token;
     }  
	 
	 
}
