package com.sino.service;

import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sino.fallback.FallbackHandler;

@Service
public class SentineTestService {
	
	
	@SentinelResource(value = "hello", fallback = "fallbackHandler",fallbackClass = FallbackHandler.class)
    public String sayHello(String name) {
		
		
        return "Hello, " + name;
    }
	

	
}
