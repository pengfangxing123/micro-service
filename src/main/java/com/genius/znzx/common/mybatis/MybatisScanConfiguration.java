package com.genius.znzx.common.mybatis;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.genius.znzx.common.mapper.TkMapper;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer <br/>
 * 如果你不使用通用Mapper，可以改为org.xxx...
 * @author fangxing.peng
 *
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解,但是该类在最初的扫描路径里，
//就会被提前加载到，然后被当作普通的@Configuration处理，会失效，暂时没找到处理办法,不过因为注入的是setSqlSessionFactoryBeanName，
//这时不会立即初始化sqlSessionFactory,传入的只是名字，非bean， 所以就算先加载也引发提前初始化问题
//@AutoConfigureAfter(MybatisConfig.class)
//@MapperScan(basePackages = { "com.genius.znzx.dao" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisScanConfiguration {
	private static Logger log = Logger.getLogger(MybatisScanConfiguration.class);
//	public MybatisScanConfiguration() {
//		log.info("*************************MybatisScanConfiguration***********************");
//	}
	
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.genius.znzx.dao");
        Properties properties = new Properties();
        // 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。 
        properties.setProperty("mappers", TkMapper.class.getName());
        //下面的方法获取不到配置文件的notEmpty，IDENTITY，因为MapperScannerConigurer实际是在解析加载bean定义阶段的，来不及获取配置文件中对应的值
        //properties.setProperty("notEmpty", trMapperConfigurationProperties.notEmpty);
        //properties.setProperty("IDENTITY", trMapperConfigurationProperties.identity);
        properties.setProperty("notEmpty","false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
    
//    @ConfigurationProperties(prefix="mapper")
//    @Component
//    public static class TrMapperConfigurationProperties{
//    	private String notEmpty;
//    	private String identity;
//    	
//		public String getNotEmpty() {
//			return notEmpty;
//		}
//		public void setNotEmpty(String notEmpty) {
//			this.notEmpty = notEmpty;
//		}
//		public String getIdentity() {
//			return identity;
//		}
//		public void setIdentity(String identity) {
//			this.identity = identity;
//		}
//    	 	
//    }
}
