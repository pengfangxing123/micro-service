package com.genius.znzx.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ZxWriteMapper {
	/**
	 * 快讯
	 * @param list
	 */
	public void insertNews(List<Map<String,Object>> list);
	/**
	 * 机会早知道
	 * @param list
	 */
	public void insertJhzzd(List<Map<String,Object>> list);
	/**
	 * 涨跌停解密
	 * @param list
	 */
	public void insertZdtjm(List<Map<String,Object>> list);
	/**
	 * 上市公司
	 * @param list
	 */
	public void insertOpenCompany(List<Map<String,Object>> list);
	
	/**
	 * 根据from_tpye得到数据库中最新日期
	 * @param sort
	 * @return
	 */
	public String queryZxDateByFromType(Map<String,Object> map);
	
	/**
	 * 删除当天和指定from_tpye的数据
	 * @param str
	 * @param date_kx
	 */
	public void deleteDatasByFromTypeAndDate(Map<String,Object> map);
}
