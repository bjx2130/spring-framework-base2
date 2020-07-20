package com.example.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class OauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
		
		/**
		 * sringsecurity访问控制列表实现参考以下链接
		 * JdbcMutableAclService
		 * https://blog.csdn.net/dongzi87/article/details/48142955
		 */
	}
}