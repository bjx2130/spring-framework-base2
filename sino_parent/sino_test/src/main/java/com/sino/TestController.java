package com.sino;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sino.service.ProductService;

@Controller
public class TestController {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	RedissonClient redissonClient;
	
	@ResponseBody
	@RequestMapping(value="test",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String g() throws Exception {
		
		RLock lock = redissonClient.getLock("order_");
		if(lock.tryLock()) {
			System.out.println("枷锁成功");
			Thread.sleep(3000);
			System.out.println("释放成功");
			lock.unlock();
		}else {
			System.out.println("枷锁失败=====================");
		}
		return "测试完成";
	}
	
	
	@Autowired
	public ProductService productService;
	
	@ResponseBody
	@RequestMapping(value="test2",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String g2() throws Exception {
		
		return this.productService.hiHystrix("kkkkkkkkkkkkk");
	}	
	

}
