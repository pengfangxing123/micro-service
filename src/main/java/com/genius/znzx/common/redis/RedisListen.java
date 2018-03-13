package com.genius.znzx.common.redis;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;

import com.mongodb.MongoClient;

/**
 * 
 * @author fangxing.peng
 *
 */
@Configuration
public class RedisListen implements Runnable{
	private static Logger logger = Logger.getLogger(RedisListen.class);	
	
	private static Subscribe subscribe;
	
	@Resource(name="redisTemplate")
	private RedisTemplate myRedisTemlet;
	
	private Thread thread;
	
	private boolean alive = false;
	
	@PostConstruct
	public void startRun() throws Exception {			
		logger.info(".......开始　订阅/发布....");
		alive = true;
		thread = new Thread(this, "jedisThread!");
		thread.start();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		logger.info("开始监听");		
		while(alive){//防止redis闪断或者重启后，应用无法自动恢复
			try {
				myRedisTemlet.execute(new RedisCallback<Object>() {
					@Override
					public Object doInRedis(RedisConnection connection)
							throws DataAccessException {
						try {
							subscribe=new Subscribe();
							connection.pSubscribe(subscribe, "pfx*".getBytes("utf-8"));
							System.out.println("###################这里不会运行 监听会卡住当前线程;");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}});
			} catch (Exception e) {
				logger.error("redis 连接异常");
				e.printStackTrace();
			}
			try {
				Thread.sleep(5000);//异常时 休眠5s；
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
		
	
}
