package com.sino;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sino.dao.ProductDao;
import com.sino.vo.Product;


@SpringBootTest
@AutoConfigureMockMvc
class PlugTest {
	
	 @Autowired
	 private ProductDao blorderDao;
	
	 @Test
	 void returnMap() {
		 
//		 List<?> list = this.blorderDao.queryList("", 1);
//		 System.out.println(list);
		 LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
		 this.blorderDao.selectPage(new Page<Product>(0,-1), queryWrapper);
	 }
	 
	 
	 
	 
//	@Test
//	void test() {
//		
//		
//		System.out.println("selectById返回结果： "+blorderDao.selectById(1191).getBlname());
//		
//		
//		LambdaQueryWrapper<Blorder> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(Blorder::getBlcount, 0);
//		queryWrapper.eq(Blorder::getBlmsg, 1);
//		
//		Page<Blorder> page = new Page<Blorder>();
//		page.setCurrent(3);//当前页
//		page.setSize(10);//单页数量
//		Page<Blorder> rp =  blorderDao.selectPage(page, queryWrapper);
//		System.out.println("selectPage返回结果： "+rp.getRecords());
//		System.out.println("selectPage返回结果： "+rp.getTotal());
//	}

}
