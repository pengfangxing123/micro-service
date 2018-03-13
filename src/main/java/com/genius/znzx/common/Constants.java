package com.genius.znzx.common;

import java.util.Date;


public class Constants {
	
	//资讯列表（快讯）：提取金融界资讯，一共三页，每页100条数据
	public static String KX_URL_1 = "http://mapp.jrj.com.cn/co/zk/1.js";
	
	public static String KX_URL_2 = "http://mapp.jrj.com.cn/co/zk/2.js";
	
	public static String KX_URL_3 = "http://mapp.jrj.com.cn/co/zk/3.js";
	
	//机会早知道
	public static String JHZZD_URL = "http://stock.jrj.com.cn/share/news/app/qingbao/";
	
	//涨跌停揭秘
	public static String ZDTJM_URL = "http://stock.jrj.com.cn/share/news/app/zhangting/";
	
	//上市公司
	public static String SSGS_URL = "http://stock.jrj.com.cn/share/news/app/company/";
	
	//资讯列表（快讯）：上次插入最新数据时间
	public static Date KX_URL_1_time = null;
	public static Date KX_URL_2_time = null;
	public static Date KX_URL_3_time = null;
	
	//机会早知道：上次插入最新数据时间
	public static Date JHZZD_URL_time = null;
	
	//涨跌停揭秘：上次插入最新数据时间
	public static Date ZDTJM_URL_time = null;
	
	//上市公司：上次插入最新数据时间
	public static Date SSGS_URL_time = null;
}
