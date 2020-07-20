package com.sinoframework.web.servlet.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.sinoframework.web.servlet.advice.ResponseControllerAdvice;
import com.sinoframework.web.servlet.advice.SinoGlobalExceptionHandler;

@Configuration
@ComponentScan(basePackages = "com.sinoframework.web.servlet.util")
public class GlobalAutoConfig {
	
	
	@Bean
	@ConditionalOnProperty(prefix = "sino.responseControllerAdvice", value = "enabled", matchIfMissing = true)
	public ResponseControllerAdvice responseControllerAdvice() {
		return new ResponseControllerAdvice();
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "sino.sinoGlobalExceptionHandler", value = "enabled", matchIfMissing = false)
	public SinoGlobalExceptionHandler sinoGlobalExceptionHandler() {
		return new SinoGlobalExceptionHandler();
	}
	
	
	
	
}
