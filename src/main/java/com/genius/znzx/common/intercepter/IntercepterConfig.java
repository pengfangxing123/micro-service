package com.genius.znzx.common.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.genius.znzx.intercepter.LoginIntercepter;

/**
 * 拦截器配置
 * @author fangxing.peng
 *
 */
@Configuration
public class IntercepterConfig extends WebMvcConfigurerAdapter {
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/hello/*.do").excludePathPatterns("/hello/index1.do");
        //registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");通配第一个*代表类，第二个*代表方法
        super.addInterceptors(registry);
    }
}	

