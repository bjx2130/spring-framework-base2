package com.sino;

import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class OauthTest {
	
	private OkHttpClient client = new OkHttpClient();
	
	@Test
	void test() throws Exception {
		String code = "kLaldh";
//		添加header: Authorization:Basic 编码(client:secret)
		String authorization=String.format("Basic %s", Base64Utils.encodeToString("rs1:secret".getBytes()));
		
		
		
		String url = String.format("http://localhost:8888/oauth/token?code=%s&grant_type=authorization_code&redirect_uri=http://localhost:7777/sino-resource1/resource&scope=all"
								,code);
		
		FormBody formboby=new FormBody.Builder()
//				 .add("phone", "18591998512")
				   .build();
		
//		MediaType JSON = MediaType.get("application/json; charset=utf-8");
//		RequestBody body = RequestBody.create(jsonStr, JSON);
		
		
		
		Response response =	client.newCall(new Request.Builder()
				.url(url)
				.post(formboby)
				.header("Authorization", authorization)
				.build()
			).execute();
		System.out.println(response.body().string());
	}
	
	//{"access_token":"b6a50615-9251-4f75-a216-a96b1df6cbd5","token_type":"bearer","refresh_token":"c4d44557-bccb-4802-b908-76b48470d15c","expires_in":43199,"scope":"app"}}
	
	
	//http://localhost:9999/hello?access_token=b6a50615-9251-4f75-a216-a96b1df6cbd5
}
