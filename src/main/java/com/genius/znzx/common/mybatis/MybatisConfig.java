package com.genius.znzx.common.mybatis;


import java.util.Properties;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.genius.znzx.common.DynamicDataSource;
import com.genius.znzx.common.transaction.MultiDataSourceTransactionFactory;
import com.github.pagehelper.PageHelper;

@Configuration
public class MybatisConfig {

	private static Logger log = Logger.getLogger(MybatisConfig.class);

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource")DynamicDataSource datasource, MybatisConfigurationProperties properties)
			throws Exception {

		log.info("*************************sqlSessionFactory:begin***********************" + properties);

		VFS.addImplClass(SpringBootVFS.class);

		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(datasource);
		//加入重写后的factory
		sessionFactory.setTransactionFactory(new MultiDataSourceTransactionFactory());
//		log.info("!!!!!!!!!!!url:"+datasource.getUrl());
//		log.info("!!!!!!!!!username"+datasource.getUsername());
//		log.info("!!!!!!!!password:"+datasource.getPassword());
		
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
	
	@Bean(name = "datasource1")
	@ConfigurationProperties(prefix = "spring.datasource.test1")
	public DataSource dataSource() {
    	return new DataSource();
	}
	
	@Bean(name = "datasource2")
	@ConfigurationProperties(prefix = "spring.datasource.test2")
	public DataSource dataSource2() {
		return new DataSource();
	}
	
    /**
     * 分页插件
     * @param properties
     * @return
     */
    @Bean
    public PageHelper pageHelper(PageHelperProperties properties) {
        log.info("注册MyBatis分页插件PageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", properties.offsetAsPageNum);
        p.setProperty("rowBoundsWithCount", properties.rowBoundsWithCount);
        p.setProperty("reasonable", properties.reasonable);
        p.setProperty("helperDialect", properties.helperDialect);
        p.setProperty("supportMethodsArguments", properties.supportMethodsArguments);
        p.setProperty("params", properties.params);
        pageHelper.setProperties(p);
        return pageHelper;
    }    
    
    /**
     * 分页插件配置
     * @author fangxing.peng
     *
     */
	@ConfigurationProperties(prefix = "pagehelper")
	@Component
    public static class PageHelperProperties{
		private String offsetAsPageNum;
		private String rowBoundsWithCount;
		private String reasonable;
		private String helperDialect;
		private String supportMethodsArguments;
		private String params;

		public String getOffsetAsPageNum() {
			return offsetAsPageNum;
		}
		public void setOffsetAsPageNum(String offsetAsPageNum) {
			this.offsetAsPageNum = offsetAsPageNum;
		}
		public String getRowBoundsWithCount() {
			return rowBoundsWithCount;
		}
		public void setRowBoundsWithCount(String rowBoundsWithCount) {
			this.rowBoundsWithCount = rowBoundsWithCount;
		}
		public String getReasonable() {
			return reasonable;
		}
		public void setReasonable(String reasonable) {
			this.reasonable = reasonable;
		}
		public String getHelperDialect() {
			return helperDialect;
		}
		public void setHelperDialect(String helperDialect) {
			this.helperDialect = helperDialect;
		}
		public String getSupportMethodsArguments() {
			return supportMethodsArguments;
		}
		public void setSupportMethodsArguments(String supportMethodsArguments) {
			this.supportMethodsArguments = supportMethodsArguments;
		}
		public String getParams() {
			return params;
		}
		public void setParams(String params) {
			this.params = params;
		}
		
    }
	
	/**
	 * mybatis配置
	 * @author fangxing.peng
	 *
	 */
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
