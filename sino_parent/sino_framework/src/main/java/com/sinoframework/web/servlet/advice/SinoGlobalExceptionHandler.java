package com.sinoframework.web.servlet.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sinoframework.web.servlet.bean.ExtIterApiRuntimeException;
import com.sinoframework.web.servlet.bean.Response;
import com.sinoframework.web.servlet.bean.ResponseResult;
import com.sinoframework.web.servlet.bean.SinoFailRuntimeException;
 
/**
 * 全局异常捕获处理
 * ExceptionHandlerExceptionResolver类 的initExceptionHandlerAdviceCache()方法会扫描@ControllerAdvice注解
 * 
 * ResponseEntity一定不能添加泛型，否则会被ResponseControllerAdvice 类在把返回值包装一次
 * 
 */
@RestControllerAdvice
public class SinoGlobalExceptionHandler {
	private String packagePath = "com.sino";
	
    private static final Logger log = LoggerFactory.getLogger(SinoGlobalExceptionHandler.class);
    
    
    
    public SinoGlobalExceptionHandler() {
    	log.info("启用【SinoGlobalExceptionHandler 全局异常】");
	}

	/**
     * 	返回自定义异常信息
     * @param ex
     * @return
     */
    @ExceptionHandler(value = SinoFailRuntimeException.class)
    public ResponseEntity errorHandler(Exception ex) {
    	
    	this.printSinoStak(ex);
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity<>(Response.makeOKRsp(ex.getMessage()),headers, HttpStatus.OK);
    }

    /**
     * 	返回自定义异常信息
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ExtIterApiRuntimeException.class)
    public ResponseEntity extIterHandler(Exception ex) {
    	this.printSinoStak(ex);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity<>(Response.makeExtIterApiRsp(),headers, HttpStatus.OK);
    }
    
    
    /**
     * 	返回固定异常信息：稍后重试
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity allExceptionHandler(Exception ex){
    	  this.printSinoStak(ex);
	      HttpHeaders headers = new HttpHeaders();
	      headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
	      return new ResponseEntity<>(Response.makeErrotRsp(),headers, HttpStatus.OK);
    	
    }
    
    
//    public ResponseEntity<ResponseBean> exceptionHandler(Exception e, HttpServletResponse response) throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
//        return new ResponseEntity<>(ResponseBean.error(e.getMessage()),headers, HttpStatus.OK);
//    }
    
    
    
    private void printSinoStak(Exception e) {
    	StackTraceElement[] els = e.getStackTrace();
    	String expStack = null;
    	for (int i = 0; i < els.length; i++) {
    		expStack = els[i].toString();
			if(expStack.toString().contains(packagePath)) {
				log.info("全局异常【线程名：{}】 【异常信息：{}】  【异常位置：{}】",Thread.currentThread().getName(),e.getMessage(),expStack);
				break;
			}
		}
    }
    
    
 
}
