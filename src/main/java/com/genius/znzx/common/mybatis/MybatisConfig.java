package com.genius.znzx.common.mybatis;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Configuration
public class MybatisConfig {

	private static Logger log = Logger.getLogger(MybatisConfig.class);

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(DataSource datasource, MybatisConfigurationProperties properties)
			throws Exception {

		log.info("*************************sqlSessionFactory:begin***********************" + properties);

		VFS.addImplClass(SpringBootVFS.class);

		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(datasource);
		log.info("!!!!!!!!!!!url:"+datasource.getUrl());
		log.info("!!!!!!!!!username"+datasource.getUsername());
		log.info("!!!!!!!!password:"+datasource.getPassword());
		sessionFactory.setTypeAliasesPackage(properties.typeAliasesPackage);
		//sessionFactory.setTypeHandlersPackage(properties.typeHandlerPackage);

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sessionFactory.setMapperLocations(resolver.getResources(properties.mapperLocations));
		
		 sessionFactory.setConfiguration(properties.configuration);

		SqlSessionFactory resultSessionFactory = sessionFactory.getObject();

		log.info("*************************sqlSessionFactory:successs:" + resultSessionFactory
				+ "***********************" + properties);

		return resultSessionFactory;

	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		DataSource db = new DataSource();
		return db;
	}

	@ConfigurationProperties(prefix = "mybatis")
	@Component
	public static class MybatisConfigurationProperties {
		private String typeAliasesPackage;
		private String typeHandlerPackage;
		private String mapperLocations;
		private org.apache.ibatis.session.Configuration configuration;

		
		public org.apache.ibatis.session.Configuration getConfiguration() {
			return configuration;
		}

		public void setConfiguration(org.apache.ibatis.session.Configuration configuration) {
			this.configuration = configuration;
		}

		public String getTypeAliasesPackage() {
			return typeAliasesPackage;
		}

		public void setTypeAliasesPackage(String typeAliasesPackage) {
			this.typeAliasesPackage = typeAliasesPackage;
		}

		public String getTypeHandlerPackage() {
			return typeHandlerPackage;
		}

		public void setTypeHandlerPackage(String typeHandlerPackage) {
			this.typeHandlerPackage = typeHandlerPackage;
		}

		public String getMapperLocations() {
			return mapperLocations;
		}

		public void setMapperLocations(String mapperLocations) {
			this.mapperLocations = mapperLocations;
		}


	}


}
