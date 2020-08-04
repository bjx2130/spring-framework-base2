package com.sino;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

public class KafkaClientTest {
	
	public static String topic = "topic_1";//定义主题
	
	@Test
	void test() {
		Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.0.187:9094");//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(p);

        try {
        	
        	while(true) {
        		
        		String msg = "终于成功了," + new Random().nextInt(100);
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, msg);
                kafkaProducer.send(record);
                System.out.println("消息发送成功:" + msg);
                
                try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
              
        } finally {
            kafkaProducer.close();
        }
	}

}
