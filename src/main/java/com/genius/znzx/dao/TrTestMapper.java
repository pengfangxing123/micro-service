package com.genius.znzx.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface TrTestMapper {

	/**
	 * 批量删除
	 * @param list
	 */
	public void deleteByIds(ArrayList<Integer> list);
	
	/**
	 * 批量插入数据
	 * @param data_list
	 */
	public void insertDatas(List<Map<String, Object>> data_list);
	
}
