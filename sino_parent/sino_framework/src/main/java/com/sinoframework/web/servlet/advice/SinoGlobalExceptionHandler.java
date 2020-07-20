package com.sinoframework.web.servlet.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseResult errorHandler(Exception ex) {
    	
    	this.printSinoStak(ex);
    	return Response.makeOKRsp(ex.getMessage());
    }

    /**
     * 	返回自定义异常信息
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ExtIterApiRuntimeException.class)
    public ResponseResult extIterHandler(Exception ex) {
    	this.printSinoStak(ex);
    	return Response.makeExtIterApiRsp(); 
    }
    
    
    /**
     * 	返回固定异常信息：稍后重试
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseResult allExceptionHandler(Exception ex){
    	this.printSinoStak(ex);
        return Response.makeErrotRsp(); 
    }
    
    
    
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
