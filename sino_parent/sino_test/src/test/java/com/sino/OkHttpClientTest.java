package com.sino;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class OkHttpClientTest {
	
	private OkHttpClient client = new OkHttpClient();
	
	@Test
	void test() throws Exception {
		
		
		String url = String.format("https://10010.sn.cn/loadData");
		
		
		
		FormBody formboby=new FormBody.Builder()
				 .add("phone", "18591998512")
				   .build();
		
//		MediaType JSON = MediaType.get("application/json; charset=utf-8");
//		RequestBody body = RequestBody.create(jsonStr, JSON);
		
		
		
		Response response =	client.newCall(new Request.Builder()
				.url(url)
				.post(formboby)
				.build()
			).execute();
		System.out.println(response.body().string());
	}
	
	
	@Test
	void exit() throws Exception {
		String url = String.format("http://localhost:8888/oauth/token");
		
		
		
		FormBody formboby=new FormBody.Builder()
				.add("access_token", "4d97fa92-9e7b-4391-a100-293314d07fe2")
				   .build();
		
//		MediaType JSON = MediaType.get("application/json; charset=utf-8");
//		RequestBody body = RequestBody.create(jsonStr, JSON);
		
		
		
		Response response =	client.newCall(new Request.Builder()
				.url(url)
				.delete(formboby)
				.build()
			).execute();
		System.out.println(response.body().string());
	}
	
	
}
