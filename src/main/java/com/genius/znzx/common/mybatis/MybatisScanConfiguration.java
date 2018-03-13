package com.genius.znzx.common.mybatis;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(MybatisConfig.class)
@MapperScan(basePackages = { "com.genius.znzx.dao" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisScanConfiguration {
	private static Logger log = Logger.getLogger(MybatisScanConfiguration.class);

	public MybatisScanConfiguration() {
		log.info("*************************MybatisScanConfiguration***********************");
	}
}
