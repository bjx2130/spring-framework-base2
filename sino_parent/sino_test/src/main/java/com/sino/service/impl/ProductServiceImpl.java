package com.sino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sino.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@SentinelResource(fallback = "defaultFallback")
	public String nohiHystrix(String name) {
		return this.restTemplate.getForObject("http://test/aaa", String.class);
	}
	
	
	@SentinelResource(fallback = "defaultFallback")
	public String hiHystrix(String name) {
		return this.restTemplate.getForObject("http://test/aaa", String.class);
	}
	
	
	public String defaultFallback(String name) {
		return "降级逻辑："+name;
	}
	
	
}
