//package com.genius.znzx.quartz;
//
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.genius.znzx.common.Constants;
//import com.genius.znzx.common.StrUtils;
//import com.genius.znzx.service.ZxIoService;
//
//
//@Component
//@Configurable
//@EnableScheduling
//public class ScheduledTasks {
//	private final static Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
//	//快讯URl
//	private static String KX_URL_1=Constants.KX_URL_1;
//	private static String KX_URL_2=Constants.KX_URL_2;
//	private static String KX_URL_3=Constants.KX_URL_3;
//	//机会早知道
//	private static String JHZZD_URL=Constants.JHZZD_URL;
//	//涨跌停揭秘
//	private static String ZDTJM_URL=Constants.ZDTJM_URL;
//	//上市公司
//	private static String SSGS_URL=Constants.SSGS_URL;
//	
//	@Autowired
//	private ZxIoService zxIoService;
//
//	/**
//	 * 快讯定时任务
//	 * 每三分钟执行一次
//	 */ 
//	@Scheduled(cron="0 0/1 2-23 * * ?") 
//    public void executeKxTask() {
//		long t1 = System.currentTimeMillis();
//        Thread current = Thread.currentThread(); 
//        //三个请求顺序不能乱；service有删除操作
////        String num_3=zxIoService.getKxDataByUrl(KX_URL_3,3);
////        String num_2=zxIoService.getKxDataByUrl(KX_URL_2,2);
//        String num_1=zxIoService.getKxDataByUrl(KX_URL_1,1);
//        long t2 = System.currentTimeMillis();
//        log.info("快讯定时任务:"+current.getId() + ",name:"+current.getName()+"耗时："+(t2 - t1));
//        log.info("KX_URL_任务插入:"+num_1+"条数据");
//    }
//	
//	/**
//	 * 机会早知道定时任务
//	*/ 
//	@Scheduled(cron="0 0/1 2-23 * * ?") 
//    public void executeJhzzdTask() {
//		long t1 = System.currentTimeMillis();
//        Thread current = Thread.currentThread();  
//        String data_str=StrUtils.getDateStr(new Date());
//        String url=JHZZD_URL+data_str+".js";
//        String num=zxIoService.getJhzzdDataByUrl(url);
//        long t2 = System.currentTimeMillis();
//        log.info("机会早知道定时任务:"+current.getId() + ",name:"+current.getName()+"耗时："+(t2 - t1));
//        log.info("机会早知道定时任务插入:"+num+"条数据");
//    }
//	
//	/**
//	 * 涨跌停揭秘定时任务
//	*/
//	@Scheduled(cron="0 0/10 2-23 * * ?") 
//    public void executeZdtjmTask() {
//		long t1 = System.currentTimeMillis();          
//        Thread current = Thread.currentThread();  
//        String data_str=StrUtils.getDateStr(new Date());
//        String url=ZDTJM_URL+data_str+".js";
//        String num=zxIoService.getZdtjmDataByUrl(url);
//        long t2 = System.currentTimeMillis();
//        log.info("涨跌停揭秘定时任务:"+current.getId() + ",name:"+current.getName()+"耗时："+(t2 - t1));
//        log.info("涨跌停揭秘定时任务插入:"+num+"条数据");
//    }
//	
//	/**
//	 * 上市公司定时任务
//	 */
//	@Scheduled(cron="0 0/10 2-23 * * ?") 
//    public void executeSsgsTask() {
//		long t1 = System.currentTimeMillis();                
//        Thread current = Thread.currentThread();  
//        String data_str=StrUtils.getDateStr(new Date());
//        String url=SSGS_URL+data_str+".js";
//        String num=zxIoService.getOpenConmpanyDataByUrl(url);
//        long t2 = System.currentTimeMillis();
//        log.info("上市公司定时任务:"+current.getId() + ",name:"+current.getName()+"耗时："+(t2 - t1));
//        log.info("上市公司定时任务插入:"+num+"条数据");
//    }
//
//}
