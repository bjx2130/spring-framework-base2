官方文档：https://docs.spring.io/spring-security/site/docs/5.0.7.BUILD-SNAPSHOT/reference/htmlsingle/


1.GET请求：http://localhost:7777/sino-auth/oauth/authorize?client_id=rs1&response_type=code&redirect_uri=http://localhost:7777/sino-resource1/resource
			重定向：https://www.baidu.com/?code=YICO47	
		
2.POST请求 ：http://localhost:7777/sino-auth/oauth/token?code=%s&grant_type=authorization_code&redirect_uri=http://localhost:7777/sino-resource1/resource&scope=all 
      添加header: Authorization:Basic 编码(client:secret)
 				返回：{
 				    "access_token": "7989cf2d-331e-4e74-80c8-506b01cd4d5f",
 				    "token_type": "bearer",
 				    "expires_in": 43199,
 				    "scope": "app"
 				}
 				
3.请求资源服务器：http://resource:port/hello?access_token=167de16c-826f-4f90-a419-e92d3e42dc3a 				