package com.sinoframework.web.servlet.interceptor.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.sinoframework.web.servlet.bean.ContextHolder;

public class ClearContextHolderInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		ContextHolder.clearCurrentThread();
		
	}

}
