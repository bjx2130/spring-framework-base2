package com.sinoframework.web.servlet.bean;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContextHolder {
	private static final Logger log = LoggerFactory.getLogger(ContextHolder.class);
	static ThreadLocal<Map<String,Object>> m = new ThreadLocal<>();
	
	public static void setPm(String key,Object value) {
		if(m.get()==null) {
			m.set(new HashMap<>());
		}
		m.get().put(key, value);
	}
	
	
	public static <T> T getPm(String key,Class<T> clazz) {
		
		if(m.get()==null) {
			return null;
		}
		
		Object o = m.get().get(key);
		if(o!=null) {
			return clazz.cast(o);
		}
		
		return null;
	}
	
	
	
	
	
	
	
	public static <T> T getPmAndRm(String key,Class<T> clazz) {
		
		if(m.get()==null) {
			return null;
		}
		
		Object o = m.get().remove(key);
		if(o!=null) {
			return clazz.cast(o);
		}
		
		return null;
	}
	
	
	
	public static void clearCurrentThread() {
		
		String threadName = Thread.currentThread().getName();
		if(m.get()==null) {
			return;
		}
		m.get().clear();
	}
	
}
