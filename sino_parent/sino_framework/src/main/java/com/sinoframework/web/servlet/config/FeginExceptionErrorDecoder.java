//package com.sinoframework.web.servlet.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sinoframework.web.servlet.bean.ExtIterApiRuntimeException;
//import com.sinoframework.web.servlet.bean.SinoFailRuntimeException;
//
//import feign.Response;
//import feign.codec.ErrorDecoder;
//
//
//@Configuration
//public class FeginExceptionErrorDecoder implements ErrorDecoder {
//		private static final Logger log = LoggerFactory.getLogger(FeginExceptionErrorDecoder.class);
//		
//		@Autowired
//        private ObjectMapper objectMapper;
//
//        @Override
//        public Exception decode(String s, Response response) {
//        		int httpStatus = response.status();
//        		String httpBody = response.body().toString();
//        		try {
//		        		if(HttpStatus.SERVICE_UNAVAILABLE.value()==httpStatus) {
//		        			ExtIterApiRuntimeException e = this.objectMapper.readValue(httpBody, ExtIterApiRuntimeException.class);
//							return new ExtIterApiRuntimeException(e.getMsg());
//		        		}else if(HttpStatus.FORBIDDEN.value()==httpStatus) {
//		        			SinoFailRuntimeException ss= this.objectMapper.readValue(httpBody, SinoFailRuntimeException.class);
//		        			return new SinoFailRuntimeException(ss.getMsg());
//		        		}
//        		} catch (JsonProcessingException e) {
//					e.printStackTrace();
//				}
//                return new RuntimeException("<FeginExceptionError异常>");
//        }
//
//}
