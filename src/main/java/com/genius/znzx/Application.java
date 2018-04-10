package com.genius.znzx;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {  
        DataSourceAutoConfiguration.class  //动态切换数据库时，防止自动配置DataSource与DataSourceTransactionManager
		})  
public class Application {
	private static Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("Applicoation start !");
	}
}
