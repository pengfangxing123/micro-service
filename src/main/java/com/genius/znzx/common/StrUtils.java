package com.genius.znzx.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 数据处理工具类
 * @author fangxing.peng
 *
 */
public class StrUtils {
	
	/**
	 * 得到指定日期2017-08-01形式
	 * @return
	 */
	public static String getDateStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(date);
        String ret_str=dateNowStr.substring(0, 10);
        return ret_str;
	}
	
	/**
	 * 字符串转日期
	 * @return
	 */
	public static Date str2Date(String str){
		if(str!=""&&str!=null){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        Date date = sdf.parse(str);
		        return date;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return null;
	}
	
	/**
	 * 得到当前天零点日期
	 * @return
	 */
	public static Date getZeroDate(){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
	}
	
	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}
	
	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
	 
	    StringBuffer string = new StringBuffer();
	 
	    String[] hex = unicode.split("\\\\u");
	 
	    for (int i = 1; i < hex.length; i++) {
	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	 
	        // 追加成string
	        string.append((char) data);
	    }
	 
	    return string.toString();
	}
}
