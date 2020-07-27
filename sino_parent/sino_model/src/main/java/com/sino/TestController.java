package com.sino;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.RateLimiter;
import com.sino.dao.ProductDao;
import com.sino.fallback.FallbackHandler;
import com.sino.service.SentineTestService;
import com.sino.vo.Product;

@Controller
public class TestController {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SentineTestService sentineTestService;
	
	
	
	@ResponseBody
	@RequestMapping(value="test",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String g() {
		
//		String url = "http://localhost:9090/getProduct/928137";
//		String prodJson =  restTemplate.getForObject(url, String.class);
//		System.out.println(prodJson);
		
		return "熔断测试";
	}
	

	
	
	
	
	/**
	 * 测试分页地址 http://localhost:9091/page?page=0&size=10&sort=pdType,asc&sort=pdTitle,desc
	 * 											  size<0  不分页
	 * 
	 * 
	 * 参考SortHandlerMethodArgumentResolver源码实现。
	 * 	默认参数规则为 sort=name,DESC
	 * 	name： 表示要排序的字段名称
	 * 	DESC 、 ASC: 正序和倒序
	 * 	排序默认为ASC
   	 * 	sort=name
	 * 	sort=name,DESC
	 * 	sort=name1,DESC&sort=name2 表示同时排序多个字段
	 * 
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="page",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object page(Page<Product> page) {
		
		LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.allEq(null, true);
		queryWrapper.eq(Product::getPdCode, "612001");
		queryWrapper.eq(Product::getPdType, 1);
		
		
		page.addOrder(new OrderItem().ascs("pd_code"));
		return productDao.selectPage(page, queryWrapper);
	}
	
	
	//每秒只发出5个令牌
    RateLimiter rateLimiter = RateLimiter.create(2.0);
    
    @ResponseBody
	@RequestMapping(value="limiter",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object tateLimiter() {
		if(rateLimiter.tryAcquire()) {
			log.info("aceess success ");
		}else {
			//log.info("aceess limit ");
		}
		return false;
		
	}
	
	
    
    @ResponseBody
	@RequestMapping(value="list",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object list(List<String> ids,@RequestParam(required = false) List<Integer> idsint) {
		log.info(ids);
		log.info(idsint);
		return false;
	}	
    
    
    
    
    
    
    
}
