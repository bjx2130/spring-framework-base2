package com.sino;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
class RestTempleteTest {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	@Test
	void test() {
		
		this.restTemplate.getForObject("http://test/aaa", String.class);
	}

}
