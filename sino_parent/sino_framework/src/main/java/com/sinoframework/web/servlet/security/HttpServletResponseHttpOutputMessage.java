package com.sinoframework.web.servlet.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;

public class HttpServletResponseHttpOutputMessage implements HttpOutputMessage {
	
	private HttpHeaders httpHeaders;
	private HttpServletResponse httpServletResponse;
	
	
	

	public HttpServletResponseHttpOutputMessage(HttpHeaders httpHeaders, HttpServletResponse httpServletResponse) {
		super();
		this.httpHeaders = httpHeaders;
		this.httpServletResponse = httpServletResponse;
	}

	@Override
	public HttpHeaders getHeaders() {
		// TODO Auto-generated method stub
		return httpHeaders;
	}

	@Override
	public OutputStream getBody() throws IOException {
		// TODO Auto-generated method stub
		Iterator<Entry<String, List<String>>>	 iter = this.httpHeaders.entrySet().iterator();
		Entry<String, List<String>> en = null;
		while(iter.hasNext()) {
			en = iter.next();
			httpServletResponse.setHeader(en.getKey(), en.getValue().get(0));
		}
		
		return httpServletResponse.getOutputStream();
	}

}
