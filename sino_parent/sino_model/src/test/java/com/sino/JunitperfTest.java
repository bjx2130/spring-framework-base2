package com.sino;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.sino.dao.ProductDao;
@SpringBootTest
@AutoConfigureMockMvc
class JunitperfTest {
	
	@Autowired
	private ProductDao productDao;
	
	@Test
	@JunitPerfConfig(threads = 300, warmUp = 2000, duration = 5000)
	void test() throws Exception {
		this.productDao.selectById(2);
	}

}
