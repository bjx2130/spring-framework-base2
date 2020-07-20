package com.sinoframework.web.servlet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sinoframework.web.servlet.interceptor.mvc.ClearContextHolderInterceptor;

/**
 * 和springmvc的webmvc拦截配置一样
 * 
 * @author BIANP
 */
@Configuration
public class SinoWebMvcConfigurer implements WebMvcConfigurer {
	private static final Logger log = LoggerFactory.getLogger(SinoWebMvcConfigurer.class);
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("启用【ClearContextHolderInterceptor 】");
		registry.addInterceptor(new ClearContextHolderInterceptor());
		
		
	}


}
