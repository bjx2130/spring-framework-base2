package com.sino;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.sino.dao.ProductDao;
import com.sino.vo.Product;
//@SpringBootTest
//@AutoConfigureMockMvc
class JunitperfTest {
	
	@Autowired
	private ProductDao productDao;
	
	OutputStreamWriter out = null;
	
	
	public JunitperfTest() throws Exception {
		out = new OutputStreamWriter( new FileOutputStream("C:\\Users\\Administrator\\Desktop\\text.txt"));
	}
	
	
	
	
//	@Test
//	@JunitPerfConfig(threads = 1, warmUp = 2000, duration = 5000)
	void test() throws Exception {
		this.productDao.selectById(2);
	}
	
	
	@Test
	@JunitPerfConfig(threads = 10, warmUp = 2000, duration = 5000)
	void writeAhead() {
		
		
//		Product pd = new Product();
//		pd.setPdId(2);
//		pd.setPdPrice(BigDecimal.ONE);
		
		//this.productDao.updateById(pd);
		
		udpmemVO();
//		try {
//			out.write("修改："+ReflectionToStringBuilder.toString(pd));
//			out.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public static Product pd = new Product();
	public synchronized static void udpmemVO() {
			pd.setPdId(2);
			pd.setPdPrice(BigDecimal.ONE);
	}
	
	
	
	@Autowired
	RedissonClient redissonClient;
	
	@Test
	@JunitPerfConfig(threads = 200, warmUp = 2000, duration = 5000)
	void redisClient() {
		RList<Object> rList = redissonClient.getList("testList");
		for(int i=0;i<200;i++) {
			rList.add("list"+i);
		}
	}
	
	
}
