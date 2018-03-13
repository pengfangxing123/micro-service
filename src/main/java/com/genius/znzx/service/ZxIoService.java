package com.genius.znzx.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genius.znzx.common.Converter;
import com.genius.znzx.common.HttpClientUtils;
import com.genius.znzx.common.StrUtils;
import com.genius.znzx.dao.ZxWriteMapper;

/**
 * 根据相应url得到数据；处理
 * @author fangxing.peng
 *
 */
@Service
public class ZxIoService {
	/** 注入*/
	@Autowired
	private ZxWriteMapper zxWriteMapper;
	@Autowired
	private HttpClientUtils httpClientUtils;
	@Autowired
	private Converter converter;
	
	
	
	public static void main(String[] args) {
		ZxIoService zxIoService = new ZxIoService();
	}
	
	/**
	 * 根据URL插入快讯资讯
	 * @param url
	 */
	@Transactional(value="mysqlTransactionManager")
	public String getKxDataByUrl(String url,int num){
		List<Map<String,Object>> data_list_kx=new ArrayList<Map<String,Object>>();
		
		/** 先删除当天日期*/
		Date date_kx=StrUtils.getZeroDate();
		HashMap<String, Object> map_kx = new HashMap<String,Object>();
		map_kx.put("from_type", "1");
		map_kx.put("date",date_kx);
		this.zxWriteMapper.deleteDatasByFromTypeAndDate(map_kx);
			
		/** 插入数据*/	
		/** 通过httpclient得到数据*/
		String str_kx=httpClientUtils.getDataByUrl(url);
		if(str_kx!=null&&str_kx!=""){
			Map<String, Object> jsonToMap_kx = Converter.jsonToMap(str_kx);
			
			/** 处理数据*/
			List<Map<String,Object>> data_kx=(List<Map<String, Object>>) jsonToMap_kx.get("data");
			if(data_kx.size()>0){
				for(int i=0;i<data_kx.size();i++){
					List<Map<String,Object>>infostocks_kx=(List) data_kx.get(i).get("infostocks");
					//得到现有数据最新数据
					Date data_date_kx=StrUtils.str2Date(String.valueOf(data_kx.get(i).get("makedate")));
					if(date_kx.before(data_date_kx)){
						//当天数据
						//infostocks为资讯关联个股，
						if(infostocks_kx.size()>0){
							//资讯有关联个股时
							for(int j=0;j<infostocks_kx.size();j++){
								HashMap<String, Object> cur_Map_kx = new HashMap<String,Object>();
								cur_Map_kx.put("news_id", data_kx.get(i).get("iiid"));
								cur_Map_kx.put("title", data_kx.get(i).get("title"));
								cur_Map_kx.put("key_word", data_kx.get(i).get("keyword"));
								cur_Map_kx.put("list_date", data_kx.get(i).get("makedate"));
								cur_Map_kx.put("channum", data_kx.get(i).get("channum"));
								cur_Map_kx.put("stk_code", infostocks_kx.get(j).get("stockcode"));
								cur_Map_kx.put("stk_sname", infostocks_kx.get(j).get("stockname"));
								cur_Map_kx.put("infocls", data_kx.get(i).get("infocls"));
								cur_Map_kx.put("detail", data_kx.get(i).get("detail"));
								cur_Map_kx.put("img_url", data_kx.get(i).get("imgurl"));
								cur_Map_kx.put("info_url", data_kx.get(i).get("infourl"));
								cur_Map_kx.put("from_type", "1");
								data_list_kx.add(cur_Map_kx);
							}
						}else{
							//资讯无关联个股时
							HashMap<String, Object> cur_Map_kx = new HashMap<String,Object>();
							cur_Map_kx.put("news_id", data_kx.get(i).get("iiid"));
							//cur_Map.put("content","");
							cur_Map_kx.put("title", data_kx.get(i).get("title"));
							cur_Map_kx.put("key_word", data_kx.get(i).get("keyword"));
							cur_Map_kx.put("list_date", data_kx.get(i).get("makedate"));
							cur_Map_kx.put("channum", data_kx.get(i).get("channum"));
							cur_Map_kx.put("stk_code", "");
							cur_Map_kx.put("stk_sname", "");
							cur_Map_kx.put("infocls", data_kx.get(i).get("infocls"));
							cur_Map_kx.put("detail", data_kx.get(i).get("detail"));
							cur_Map_kx.put("img_url", data_kx.get(i).get("imgurl"));
							cur_Map_kx.put("info_url", data_kx.get(i).get("infourl"));
							cur_Map_kx.put("from_type", "1");
							//cur_Map.put("cls", "");
							data_list_kx.add(cur_Map_kx);
						}
						
					}
					
				}
				if(data_list_kx.size()>0){
					zxWriteMapper.insertNews(data_list_kx);
				}
				return data_list_kx.size()+"";
			}
		}
		return null;
		
	}
	
	/**
	 * 根据URL得到机会早知道数据，并插入
	 * @param url
	 */
	@Transactional(value="mysqlTransactionManager")
	public String  getJhzzdDataByUrl(String url){
		
		List<Map<String,Object>> data_list_jh=new ArrayList<Map<String,Object>>();
		
		/** 删除当天数据*/
		Date date_jh=StrUtils.getZeroDate();
		HashMap<String, Object> map_jh = new HashMap<String,Object>();
		map_jh.put("from_type", "2");
		map_jh.put("date",date_jh);
		this.zxWriteMapper.deleteDatasByFromTypeAndDate(map_jh);
		
		/** 插入当天数据*/
		String str_jh=httpClientUtils.getDataByUrl(url);
		
		if(str_jh!=null&&str_jh!=""){
			str_jh=str_jh.substring(str_jh.indexOf("{"));
			str_jh=str_jh.substring(0,str_jh.lastIndexOf(";"));

			Map<String, Object> jsonToMap_jh = Converter.jsonToMap(str_jh);
			List<Map<String,Object>> newsinfo_jh=(List<Map<String, Object>>) jsonToMap_jh.get("newsinfo");
			
			if(newsinfo_jh.size()>0){
				for(int i=0;i<newsinfo_jh.size();i++){
					String str_code_jh=String.valueOf(newsinfo_jh.get(i).get("stockcode"));
					String[] split_jh = str_code_jh.split(",");
					//得到现有数据最新数据
					Date data_date_jh=StrUtils.str2Date(String.valueOf(newsinfo_jh.get(i).get("makedate")));
					if(date_jh.before(data_date_jh)){
						//当天数据
						//infostocks为资讯关联个股		
						if(split_jh.length>0){
							for(int j=0;j<split_jh.length;j++){
								HashMap<String, Object> cur_Map_jh = new HashMap<String,Object>();
								cur_Map_jh.put("news_id", newsinfo_jh.get(i).get("iiid"));
								cur_Map_jh.put("title", newsinfo_jh.get(i).get("title"));
								cur_Map_jh.put("content",newsinfo_jh.get(i).get("content"));
								cur_Map_jh.put("key_word", newsinfo_jh.get(i).get("keyword"));
								cur_Map_jh.put("list_date", newsinfo_jh.get(i).get("makedate"));
								//cur_Map.put("channum", newsinfo.get(i).get("channum"));
								cur_Map_jh.put("stk_code", split_jh[j]);
								cur_Map_jh.put("infocls", newsinfo_jh.get(i).get("infocls"));
								cur_Map_jh.put("detail", newsinfo_jh.get(i).get("detail"));
								cur_Map_jh.put("img_url", newsinfo_jh.get(i).get("imgurl"));
								cur_Map_jh.put("cls", 100001);
								//cur_Map.put("info_url", newsinfo.get(i).get("infourl"));
								cur_Map_jh.put("from_type", "2");
								data_list_jh.add(cur_Map_jh);
							}
						}else{
							HashMap<String, Object> cur_Map_jh = new HashMap<String,Object>();
							cur_Map_jh.put("news_id", newsinfo_jh.get(i).get("iiid"));
							cur_Map_jh.put("title", newsinfo_jh.get(i).get("title"));
							cur_Map_jh.put("content",newsinfo_jh.get(i).get("content"));
							cur_Map_jh.put("key_word", newsinfo_jh.get(i).get("keyword"));
							cur_Map_jh.put("list_date", newsinfo_jh.get(i).get("makedate"));
							//cur_Map.put("channum", newsinfo.get(i).get("channum"));
							cur_Map_jh.put("stk_code", "");
							cur_Map_jh.put("infocls", newsinfo_jh.get(i).get("infocls"));
							cur_Map_jh.put("detail", newsinfo_jh.get(i).get("detail"));
							cur_Map_jh.put("img_url", newsinfo_jh.get(i).get("imgurl"));
							cur_Map_jh.put("cls", 100001);
							//cur_Map.put("info_url", newsinfo.get(i).get("infourl"));
							cur_Map_jh.put("from_type", "2");
							data_list_jh.add(cur_Map_jh);
						}
					}
				}
				
				if(data_list_jh.size()>0){
					zxWriteMapper.insertJhzzd(data_list_jh);	
				}
				return data_list_jh.size()+"";
			}
			
		}	
		//无数据时返回Null
		return null;
	}
	
	/**
	 * 根据URL得到涨跌停数据，并插入
	 * @param url
	 */
	@Transactional(value="mysqlTransactionManager")
	public String getZdtjmDataByUrl(String url){
		
		List<Map<String,Object>> data_list_zdt=new ArrayList<Map<String,Object>>();
		
		/** 删除当天数据*/
		Date date_zdt=StrUtils.getZeroDate();
		HashMap<String, Object> map_zdt = new HashMap<String,Object>();
		map_zdt.put("from_type", "3");
		map_zdt.put("date",date_zdt);
		this.zxWriteMapper.deleteDatasByFromTypeAndDate(map_zdt);
		
		/** 插入当天数据*/
		String str_zdt=httpClientUtils.getDataByUrl(url);
		if(str_zdt!=null&&str_zdt!=""){
			str_zdt=str_zdt.substring(str_zdt.indexOf("{"));
			str_zdt=str_zdt.substring(0,str_zdt.lastIndexOf(";"));

			Map<String, Object> jsonToMap = Converter.jsonToMap(str_zdt);
			List<Map<String,Object>> newsinfo_zdt=(List<Map<String, Object>>) jsonToMap.get("newsinfo");
			
			if(newsinfo_zdt.size()>0){
				for(int i=0;i<newsinfo_zdt.size();i++){
					String str_code_zdt=String.valueOf(newsinfo_zdt.get(i).get("stockcode"));
					String[] split_zdt = str_code_zdt.split(",");
					//得到现有数据最新数据
					Date data_date_zdt=StrUtils.str2Date(String.valueOf(newsinfo_zdt.get(i).get("makedate")));
					
					if(date_zdt.before(data_date_zdt)){
						//当天数据
						//infostocks为资讯关联个股
						if(split_zdt.length>0){
							for(int j=0;j<split_zdt.length;j++){
								HashMap<String, Object> cur_Map_zdt = new HashMap<String,Object>();
								cur_Map_zdt.put("news_id", newsinfo_zdt.get(i).get("iiid"));
								cur_Map_zdt.put("title", newsinfo_zdt.get(i).get("title"));
								cur_Map_zdt.put("content",newsinfo_zdt.get(i).get("content"));
								cur_Map_zdt.put("key_word", newsinfo_zdt.get(i).get("keyword"));
								cur_Map_zdt.put("list_date", newsinfo_zdt.get(i).get("makedate"));
								//cur_Map.put("channum", newsinfo.get(i).get("channum"));
								cur_Map_zdt.put("stk_code", split_zdt[j]);
								cur_Map_zdt.put("infocls", newsinfo_zdt.get(i).get("infocls"));
								cur_Map_zdt.put("detail", newsinfo_zdt.get(i).get("detail"));
								cur_Map_zdt.put("img_url", newsinfo_zdt.get(i).get("imgurl"));
								cur_Map_zdt.put("cls", 100002);
								//cur_Map.put("info_url", newsinfo.get(i).get("infourl"));
								cur_Map_zdt.put("from_type", "3");
								data_list_zdt.add(cur_Map_zdt);
							}
						}else{
							HashMap<String, Object> cur_Map_zdt = new HashMap<String,Object>();
							cur_Map_zdt.put("news_id", newsinfo_zdt.get(i).get("iiid"));
							cur_Map_zdt.put("title", newsinfo_zdt.get(i).get("title"));
							cur_Map_zdt.put("content",newsinfo_zdt.get(i).get("content"));
							cur_Map_zdt.put("key_word", newsinfo_zdt.get(i).get("keyword"));
							cur_Map_zdt.put("list_date", newsinfo_zdt.get(i).get("makedate"));
							//cur_Map.put("channum", newsinfo.get(i).get("channum"));
							cur_Map_zdt.put("stk_code", "");
							cur_Map_zdt.put("infocls", newsinfo_zdt.get(i).get("infocls"));
							cur_Map_zdt.put("detail", newsinfo_zdt.get(i).get("detail"));
							cur_Map_zdt.put("img_url", newsinfo_zdt.get(i).get("imgurl"));
							cur_Map_zdt.put("cls", 100002);
							//cur_Map.put("info_url", newsinfo.get(i).get("infourl"));
							cur_Map_zdt.put("from_type", "3");
							data_list_zdt.add(cur_Map_zdt);
						}	
					}
				}
			
				if(data_list_zdt.size()>0){
					zxWriteMapper.insertZdtjm(data_list_zdt);	
				}
				
				return data_list_zdt.size()+"";
			}
		}
		
		//无数据时返回Null
		return null;
	}
	
	/**
	 * 根据URL得到上市公司数据，并插入
	 * @param str
	 */
	@Transactional(value="mysqlTransactionManager")
	public String getOpenConmpanyDataByUrl(String url){
		List<Map<String,Object>> data_list_gs=new ArrayList<Map<String,Object>>();
		
		/** 删除当天数据*/
		Date date_gs=StrUtils.getZeroDate();
		HashMap<String, Object> map_gs = new HashMap<String,Object>();
		map_gs.put("from_type", "4");
		map_gs.put("date",date_gs);
		this.zxWriteMapper.deleteDatasByFromTypeAndDate(map_gs);
		
		/** 插入当天数据*/
		String str_gs=httpClientUtils.getDataByUrlAndChart(url,"utf-8");
		
		if(str_gs!=null&&str_gs!=""){
			str_gs=str_gs.substring(str_gs.indexOf("{"));
			str_gs=str_gs.substring(0,str_gs.lastIndexOf(";"));

			Map<String, Object> jsonToMap_gs = Converter.jsonToMap(str_gs);
			List<List<Map<String,Object>>> newsinfo_gs=(List<List<Map<String, Object>>>) jsonToMap_gs.get("newsinfo");
			
			if(newsinfo_gs.size()>0){
				for(int i=0;i<newsinfo_gs.size();i++){
					//得到相关股票数组
					String str_code_gs=String.valueOf(newsinfo_gs.get(i).get(0).get("stockcode"));
					String[] split_gs = str_code_gs.split(",");
					
					String str_name_gs=String.valueOf(newsinfo_gs.get(i).get(0).get("stockname"));
					String[] split_name_gs = str_name_gs.split(",");
					
					//得到现有数据最新数据
					Date data_date_gs=StrUtils.str2Date(String.valueOf(newsinfo_gs.get(i).get(0).get("makedate")));
					
					if(date_gs.before(data_date_gs)){
						//当天数据
						//infostocks为资讯关联个股
						if(split_gs.length>0){
							for(int j=0;j<split_gs.length;j++){
								HashMap<String, Object> cur_Map_gs = new HashMap<String,Object>();
								cur_Map_gs.put("news_id", newsinfo_gs.get(i).get(0).get("iiid"));
								cur_Map_gs.put("title", newsinfo_gs.get(i).get(0).get("title"));
								//cur_Map.put("content",newsinfo.get(i).get(0).get("content"));
								//cur_Map.put("key_word", newsinfo.get(i).get(0).get("keyword"));
								cur_Map_gs.put("list_date", newsinfo_gs.get(i).get(0).get("makedate"));
								//cur_Map.put("channum", newsinfo.get(i).get("channum"));
								cur_Map_gs.put("stk_code", split_gs[j]);
								cur_Map_gs.put("stk_sname", split_name_gs[j]);
								cur_Map_gs.put("infocls", newsinfo_gs.get(i).get(0).get("infocls"));
								cur_Map_gs.put("detail", newsinfo_gs.get(i).get(0).get("detail"));
								cur_Map_gs.put("img_url", newsinfo_gs.get(i).get(0).get("imgurl"));
								cur_Map_gs.put("cls",100003);
								//cur_Map.put("info_url", newsinfo.get(i).get("infourl"));
								cur_Map_gs.put("from_type", "4");
								data_list_gs.add(cur_Map_gs);
							}
						}else{
							HashMap<String, Object> cur_Map_gs = new HashMap<String,Object>();
							cur_Map_gs.put("news_id", newsinfo_gs.get(i).get(0).get("iiid"));
							cur_Map_gs.put("title", newsinfo_gs.get(i).get(0).get("title"));
							//cur_Map.put("content",newsinfo.get(i).get(0).get("content"));
							//cur_Map.put("key_word", newsinfo.get(i).get(0).get("keyword"));
							cur_Map_gs.put("list_date", newsinfo_gs.get(i).get(0).get("makedate"));
							//cur_Map.put("channum", newsinfo.get(i).get("channum"));
							cur_Map_gs.put("stk_code", "");
							cur_Map_gs.put("stk_sname", "");
							cur_Map_gs.put("infocls", newsinfo_gs.get(i).get(0).get("infocls"));
							cur_Map_gs.put("detail", newsinfo_gs.get(i).get(0).get("detail"));
							cur_Map_gs.put("img_url", newsinfo_gs.get(i).get(0).get("imgurl"));
							cur_Map_gs.put("cls",100003);
							//cur_Map.put("info_url", newsinfo.get(i).get("infourl"));
							cur_Map_gs.put("from_type", "4");
							data_list_gs.add(cur_Map_gs);
						}		
					}
				}
			
				if(data_list_gs.size()>0){
					zxWriteMapper.insertOpenCompany(data_list_gs);
				}
				
				return data_list_gs.size()+"";
			}
			
		}
		
		//无数据是返回null
		return null;
	}
	
	
	/**
	 * 根据from_tpye得到数据库中最新日期
	 * @param num
	 * @return
	 */
	public String queryZxDateByFromType(int num){
		String sort="";
		if(num==1)  sort="1";
		if(num==2)  sort="2";
		if(num==3)  sort="3";
		if(num==4)  sort="4";
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("key", sort);
		String date_str=this.zxWriteMapper.queryZxDateByFromType(map);
		return date_str;
		
	}
	
}
