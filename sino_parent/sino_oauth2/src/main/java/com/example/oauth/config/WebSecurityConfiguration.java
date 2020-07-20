package com.example.oauth.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.oauth.authentication.handler.CustomAuthenticationFailureHandler;
import com.example.oauth.authentication.handler.CustomSavedRequestAwareAuthenticationSuccessHandler;
import com.example.oauth.secutity.filter.SmsCodeAuthenticationFilter;

@AutoConfigureAfter(value = SpringSecutityBeanConfiguration.class)
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 	配置所有请求的安全验证
		注入Bean UserDetailsService
		注入Bean AuthenticationManager 用来做验证
		注入Bean PasswordEncoder
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	
        http
        	//扩展短信验证码登录
        	//.addFilterAt(new SmsCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            	.antMatchers("/oauth/**","/static/**").permitAll()
//                .antMatchers("/", "/home").permitAll()//不需要认证就可以访问
                .antMatchers("/**").hasAnyRole("USER")//需要身份验证
                .and().csrf().disable()
            .formLogin()
	            .loginPage("/sinoLogin").loginProcessingUrl("/login").permitAll()
            	.successHandler(customSavedRequestAwareAuthenticationSuccessHandler())
            	.failureUrl("/sinoLogin?error");
            	;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// https://docs.spring.io/spring-security/site/docs/5.0.7.BUILD-SNAPSHOT/reference/htmlsingle/
//==========使用内存存储用户名信息=============
//		auth.inMemoryAuthentication().passwordEncoder(bcryptPasswordEncoder).withUser("admin")
//				.password(bcryptPasswordEncoder.encode("111")).roles("USER").and().withUser("admin2")
//				.password(bcryptPasswordEncoder.encode("222")).roles("ADMIN");
  
    	
//==========使用数据库存储用户名信息=============   	
    	auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);
    	
    }
    
    
    
	public SavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler() {
		return new CustomSavedRequestAwareAuthenticationSuccessHandler();
	}
    
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}
	
}
