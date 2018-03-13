package com.genius.znzx.common.redis;


import javax.annotation.Resource;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * MessageListener
 * @author fangxing.peng
 *
 */
@Service
public class Subscribe implements MessageListener {
//	@Resource(name="redisTemplate")
//	private RedisTemplate myRedisTemlet;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		 byte[] body = message.getBody();//请使用valueSerializer  
	     byte[] channel = message.getChannel();  
	     try {
			String str_body = new String(body,"utf-8");
			String channel_body = new String(channel,"utf-8");
			System.out.println("str_body："+str_body+";channel_body："+channel_body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     //请参考配置文件，本例中key，value的序列化方式均为string。  
	     //其中key必须为stringSerializer。和redisTemplate.convertAndSend对应  
//	     String itemValue = (String)myRedisTemlet.getValueSerializer().deserialize(body);  
//	     String topic = (String)myRedisTemlet.getStringSerializer().deserialize(channel);  
//	     System.out.println("itemValue："+itemValue+";topic："+topic);
		
	}
	
}
