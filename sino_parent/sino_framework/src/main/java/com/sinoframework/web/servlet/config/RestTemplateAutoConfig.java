package com.sinoframework.web.servlet.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.sinoframework.web.servlet.interceptor.resttemplate.MixLoadBalancerInterceptor;
import com.sinoframework.web.servlet.util.ExceptionUtil;

@Configuration
public class RestTemplateAutoConfig {
	private static final Logger log = LoggerFactory.getLogger(RestTemplateAutoConfig.class);
	
	
	@Bean
	@LoadBalanced
	@SentinelRestTemplate(fallback = "fallback", fallbackClass = ExceptionUtil.class, blockHandler="handleException",blockHandlerClass=ExceptionUtil.class)
	public RestTemplate restTemplate() {
		log.info("启用【RestTemplate 实例】");
		return new RestTemplate();
	}
	
	
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		return new StringHttpMessageConverter(StandardCharsets.UTF_8);
	}
	
	@Bean
	public MixLoadBalancerInterceptor mixLoadBalancerInterceptor(@Autowired RestTemplateBuilder restTemplateBuilder) {
		return new MixLoadBalancerInterceptor(restTemplateBuilder.buildRequestFactory());
	}
	
	
	/**
	 * @LoadBalanced 支持ip、域名、服务名 调用
	 * 	其它配置参考类 MixLoadBalancerInterceptor
	 * 
	 * @param restTemplates
	 * @param restTemplateBuilder
	 * @return
	 */
    @Bean
    @ConditionalOnProperty(prefix="mix.loadbalancer",name = "enable", matchIfMissing = true)
    public SmartInitializingSingleton mixLoadBalancedRestTemplateInitializer(
            @Autowired List<RestTemplate> restTemplates,
            @Autowired MixLoadBalancerInterceptor mixLoadBalancerInterceptor,
            @Autowired StringHttpMessageConverter stringHttpMessageConverter_Utf8) {
    	
    	log.info("启用【RestTemplate 支持ip、域名、服务名 调用,替换默认StringHttpMessageConverter转换器编码为utf-8】");
    	
        return () -> {
            for (RestTemplate restTemplate : restTemplates) {
                List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
                list.add(mixLoadBalancerInterceptor);
                
                //设置拦截器
                restTemplate.setInterceptors(list);
                //替换默认StringHttpMessageConverter转换器编码为utf-8
                restTemplate.getMessageConverters().set(1, stringHttpMessageConverter_Utf8);
            }
        };
    }
    
	
	
}
