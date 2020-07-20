package com.sino;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
class PlugTest {
//	WebSecurityConfigurerAdapter
//	 @Autowired
//	 private BlorderDao blorderDao;
//	
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
