package com.resource.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.sinoframework.web.servlet.bean.Response;

/**
 * 资源服务器
 * @author Administrator
 *
 */
@Configuration
@EnableResourceServer
public class ResServerConfig extends ResourceServerConfigurerAdapter {
	
	private static final Logger log = LogManager.getLogger();
//	
//	RemoteTokenServices 用于第三方应用【向远程认证服务器验证token，同时获取token对应的用户的信息】 
//	@Autowired
//	private RemoteTokenServices tokenServices;
//
//	@Primary
//	@Bean
//	public RemoteTokenServices tokenService() {
//		RemoteTokenServices tokenService = new RemoteTokenServices();
//		tokenService.setCheckTokenEndpointUrl("http://localhost:8888/oauth/check_token");
//		tokenService.setClientId("client");
//		tokenService.setClientSecret("secret");
//		return tokenService;
//	}
	@Autowired
	private AuthenticationEntryPoint jsonAuthenticationEntryPoint;
	
	@Autowired
	private ResourceServerTokenServices tokenServices;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices)
			.stateless(true);
	}
	
	/**
	 * 使用spring security 我们可以准确控制session何时创建以及Spring Security如何与之交互：
		always – a session will always be created if one doesn’t already exist，没有session就创建。
		ifRequired – a session will be created only if required (default)，如果需要就创建（默认）。
		never – the framework will never create a session itself but it will use one if it already exists
		stateless – no session will be created or used by Spring Security 不创建不使用session
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		/*
		 * 注意： 1、必须先加上：
		 * .requestMatchers().antMatchers(...)，表示对资源进行保护，也就是说，在访问前要进行OAuth认证。
		 * 2、接着：访问受保护的资源时，要具有哪里权限。 ------------------------------------
		 * 否则，请求只是被Security的拦截器拦截，请求根本到不了OAuth2的拦截器。
		 * 同时，还要注意先配置：security.oauth2.resource.filter-order=3，否则通过access_token取不到用户信息。
		 * ------------------------------------ requestMatchers()部分说明： Invoking
		 * requestMatchers() will not override previous invocations of ::
		 * mvcMatcher(String)}, requestMatchers(), antMatcher(String),
		 * regexMatcher(String), and requestMatcher(RequestMatcher).
		 */
		http
				// Since we want the protected resources to be accessible in the UI as well we
				// need
				// session creation to be allowed (it's disabled by default in 2.0.6)
				// 另外，如果不设置，那么在通过浏览器访问被保护的任何资源时，每次是不同的SessionID，并且将每次请求的历史都记录在OAuth2Authentication的details的中
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().requestMatchers().antMatchers("/user", "/res/**")
				.and().authorizeRequests().antMatchers("/user", "/res/**")
				.authenticated()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(jsonAuthenticationEntryPoint)
				;
	}
}