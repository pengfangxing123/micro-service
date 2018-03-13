package com.genius.znzx.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genius.znzx.dao.TrTestMapper;

@Service
public class TranscationTest {
	
	@Autowired
	private TrTestMapper trTestMapper;
	
	@Transactional(value="mysqlTransactionManager")
	public void testTranscation (){
		//删除数据；
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=1;i<5;i++){
			list.add(i);	
		}
		trTestMapper.deleteByIds(list);
		//重新插入数据
		List<Map<String,Object>> data_list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<5;i++){
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("id",i);
			map.put("name",i+"_name");
			map.put("age",i+"_age");
			data_list.add(map);
		}
		trTestMapper.insertDatas(data_list);
	}
	
	public void insertTest (){	
		//插入数据
		List<Map<String,Object>> data_list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<5;i++){
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("id",i);
			map.put("name",i+"_name");
			map.put("age",i+"_age");
			data_list.add(map);
		}
		trTestMapper.insertDatas(data_list);
	}
}
