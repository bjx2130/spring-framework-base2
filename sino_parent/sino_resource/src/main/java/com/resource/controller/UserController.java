package com.resource.controller;

import java.security.Principal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	
	@RequestMapping(value="user",produces = MediaType.APPLICATION_JSON_VALUE)
	public Object user(Principal principal) {
		//principal在经过security拦截后，是org.springframework.security.authentication.UsernamePasswordAuthenticationToken
		//在经OAuth2拦截后，是OAuth2Authentication
	    return principal;
	}
	
	
	@RequestMapping("res/getMsg2")
	public String getMsg( Principal principal) {// principal中封装了客户端（用户，也就是clientDetails，区别于Security的UserDetails，其实clientDetails中也封装了UserDetails），不是必须的参数，除非你想得到用户信息，才加上principal。
		return "Get the msg: " ;
	}
}
