package com.sino;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootTest
@AutoConfigureMockMvc
class KafkaTest {
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
	
	@Test
	void test() {
		
		
		ListenableFuture<SendResult<String, Object>> sendFuture = kafkaTemplate.send("topic_1", "测试数据");
		sendFuture.addCallback(
				result->{
						log.info("发送成功:"+result);
				}, 
				ex->{
						log.info("发送失败:"+ex.getMessage());
				});
	}

}
