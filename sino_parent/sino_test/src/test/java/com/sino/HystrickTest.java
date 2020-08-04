package com.sino;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sino.service.ProductService;

@SpringBootTest
class HystrickTest {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	public ProductService productService;
	
	@Test
	void test() {
		String a = this.productService.hiHystrix("你好");
		log.info(a);
	}

}
