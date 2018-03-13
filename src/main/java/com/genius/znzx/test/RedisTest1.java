package com.genius.znzx.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest1 {
	@Resource(name="redisTemplate_test")
	private RedisTemplate<String, Object> myRedisTemlet;
	
	@Test
	public void test1(){
		myRedisTemlet.opsForValue().set("test1", "pengfangxing");
		Object object = myRedisTemlet.opsForValue().get("12345");
		System.out.println(object.toString());
		
//		Jedis jedis1 = new Jedis("172.18.135.18", 6379);
//		jedis1.auth("123.com");
//		jedis1.set("12345","546");
	}
	
}
