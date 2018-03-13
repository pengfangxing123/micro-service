package com.genius.znzx.common.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;


/**
 * 重构redisTemplate
 * 需要连多个redis再配置一个本类，或者把三个bean重新写一个
 * @author fangxing.peng
 *
 */
@Configuration
public class RedisConfig {
	private static Logger logger = Logger.getLogger(RedisConfig.class);
	
	@Bean(name="jedisPoolConfig")
	@ConfigurationProperties(prefix = "redis")
	public JedisPoolConfig getJedisPool(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		return jedisPoolConfig;
	}
	
	@Bean(name="jedisConnectionFactory")
	@Autowired
	@Qualifier("jedisPoolConfig")
	@ConfigurationProperties(prefix = "redis")
	public JedisConnectionFactory getJedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setPoolConfig(jedisPoolConfig);
		logger.info("JedisConnectionFactory bean init success.");
		return factory;
	}
	
	//必须要有一个bean名字为redisTemplate的来覆盖源代码中原生的redisTemplate;
	//原生的redisTemplate使用的是按JedisConnectionFactory查找;这样的话就会有多个就会报错
	@Bean(name="redisTemplate")
	@Autowired
	@Qualifier("jedisConnectionFactory")
	public StringRedisTemplate getStringRedisTemplate(JedisConnectionFactory jedisConnectionFactory){
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory);
		return stringRedisTemplate;
	}
}

