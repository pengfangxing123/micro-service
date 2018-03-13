package com.genius.znzx.common.upload;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class UploadConfig {
	@Bean
	
    public MultipartConfigElement multipartConfigElement(UploadConfigProperties properties) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        factory.setMaxFileSize(properties.maxFileSize); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(properties.maxRequestSize);
        // 路径地址 （如果我们没有使用绝对路径的话，transferTo方法会在相对路径前添加一个location路径,但是不能使用f.mkdirs()；因为location是后来加上的所以注释掉）
        //factory.setLocation(properties.location);
        return factory.createMultipartConfig();
    }
	
	@Component
	@ConfigurationProperties(prefix = "upload")
	public static class UploadConfigProperties{
		private String maxFileSize;
		private String maxRequestSize;
		private String location;
		public String getMaxFileSize() {
			return maxFileSize;
		}
		public void setMaxFileSize(String maxFileSize) {
			this.maxFileSize = maxFileSize;
		}
		public String getMaxRequestSize() {
			return maxRequestSize;
		}
		public void setMaxRequestSize(String maxRequestSize) {
			this.maxRequestSize = maxRequestSize;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		
	}
}
