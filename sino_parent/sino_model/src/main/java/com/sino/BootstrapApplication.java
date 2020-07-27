package com.sino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringCloudApplication
public class BootstrapApplication {
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(BootstrapApplication.class, args);
	}
	
}
