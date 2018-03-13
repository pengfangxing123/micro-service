package com.genius.znzx.test;


import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.genius.znzx.common.redis.RedisListen;
import com.genius.znzx.common.redis.Subscribe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisListenTest {
	@Autowired
	private Subscribe subscribe;
	
	@Resource(name="redisTemplate")
	private RedisTemplate myRedisTemlet;
	
	@Autowired
	private RedisListen redisListen;
	
	@SuppressWarnings("unchecked")
	@Test
	public void test1(){
		//myRedisTemlet.convertAndSend("pfx", "sa");下面方法的封装；没有返回值，无法判断几个接受到消息
		myRedisTemlet.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) {
				try {
					Long publish = connection.publish("pfx".getBytes("utf-8"), "sa".getBytes("utf-8"));
					System.out.println(publish);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
				}
				return null;
			}
		}, true);
//		myRedisTemlet.execute(new RedisCallback<Object>() {
//
//			@Override
//			public Object doInRedis(RedisConnection connection)
//					throws DataAccessException {
//				try {
//					connection.pSubscribe(subscribe, "pfx*".getBytes("utf-8"));
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return null;
//			}		
//		});
		//redisListen.listenRedis();
		
	}
}
