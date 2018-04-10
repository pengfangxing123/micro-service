package com.genius.znzx.common.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

/**
 * 重构mongoTemplate
 * @author fangxing.peng
 *
 */
@Configuration
public class MongodbConfig {
	private static Logger log = Logger.getLogger(MongodbConfig.class);
	
	@Bean(name="mongoClient")
	public MongoClient mongoClient(MongodbConfigProperties properties) throws Exception{
		log.info("*************************mongoClient:begin***********************" + properties);
		MongoClientOptions options=new MongoClientOptions.Builder()
								.connectTimeout(properties.connectTimeout)// 链接超时时间
								.socketTimeout(properties.socketTimeout)// read数据超时时间
								.connectionsPerHost(properties.perHost)// 每个地址最大请求数
								.maxWaitTime(properties.waitTime)// 长链接的最大等待时间
								.threadsAllowedToBlockForConnectionMultiplier(properties.connectionMultiplier)// 一个socket最大的等待请求数
								.build();
		List<ServerAddress> addsList = new ArrayList<ServerAddress>();
		for(int i=0;i<properties.hosts.size();i++){
			ServerAddress serverAddress = new ServerAddress(properties.hosts.get(i) , properties.port);
			addsList.add(serverAddress);
		}
		MongoClient mongoClient=new MongoClient(addsList,options);
		log.info("*************************mongoClient:end:" + mongoClient
				+ "***********************" + properties);
		return mongoClient;
	}
	
	@Bean(name="mongoDbFactory")
	@Autowired
	@Qualifier("mongoClient")
	public MongoDbFactory getMongoDbFactory(MongoClient mongoClient ,MongodbConfigProperties properties) throws Exception {
		return new SimpleMongoDbFactory(mongoClient,
				properties.dbname);
	}
	
	
//	@Bean (name="mappingMongoConverter")//使用自定义的typeMapper去除写入mongodb时的“_class”字段 
//	@Autowired
//	@Qualifier("mongoDbFactory")
//	public MappingMongoConverter getMappingMongoConverter(MongoDbFactory mongoDbFactory) throws Exception {  
//	    DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);  
//	    MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());  
//	    converter.setTypeMapper(new DefaultMongoTypeMapper(null));  
//	    return converter;  
//	}  
		
	@Bean(name="mongoTemplate")
	@Autowired
	@Qualifier(value="mongoDbFactory")
	public MongoTemplate getMongoTemplate(MongoDbFactory mongoDbFactory)throws Exception{
		//使用自定义的typeMapper去除写入mongodb时的“_class”字段
		DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory,converter);
		mongoTemplate.setReadPreference(ReadPreference.secondaryPreferred());//读写分离
		return mongoTemplate;
	}
	
	@ConfigurationProperties(prefix = "mongodb")
	@Component
	public static class MongodbConfigProperties{
		private int port;
		private List<String> hosts;
		private int connectTimeout;
		private int socketTimeout;
		private int perHost;
		private int waitTime;
		private int connectionMultiplier;
		private String dbname;
		
		
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public List<String> getHosts() {
			return hosts;
		}
		public void setHosts(List<String> hosts) {
			this.hosts = hosts;
		}
		public int getConnectTimeout() {
			return connectTimeout;
		}
		public void setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		public int getSocketTimeout() {
			return socketTimeout;
		}
		public void setSocketTimeout(int socketTimeout) {
			this.socketTimeout = socketTimeout;
		}
		public int getPerHost() {
			return perHost;
		}
		public void setPerHost(int perHost) {
			this.perHost = perHost;
		}
		public int getWaitTime() {
			return waitTime;
		}
		public void setWaitTime(int waitTime) {
			this.waitTime = waitTime;
		}
		public int getConnectionMultiplier() {
			return connectionMultiplier;
		}
		public void setConnectionMultiplier(int connectionMultiplier) {
			this.connectionMultiplier = connectionMultiplier;
		}
		public String getDbname() {
			return dbname;
		}
		public void setDbname(String dbname) {
			this.dbname = dbname;
		}
		
			
	}
}

//@Configuration
//public class MockConfiguration{
//    @Bean
//    public MockService mockService(){
//        return new MockServiceImpl(dependencyService());
//    }
//    
//    @Bean
//    public DependencyService dependencyService(){
//        return new DependencyServiceImpl();
//    }
//}
//@Configuration为JavaConfig配置类上面配置等于下面再xml的配置
//<bean id="mockService" class="..MockServiceImpl">
//<propery name ="dependencyService" ref="dependencyService" />
//</bean>
//
//<bean id="dependencyService" class="DependencyServiceImpl"></bean>
