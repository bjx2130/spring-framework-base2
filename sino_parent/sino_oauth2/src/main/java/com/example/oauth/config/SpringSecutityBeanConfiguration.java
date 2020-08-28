package com.example.oauth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class SpringSecutityBeanConfiguration {
	
    /**
     * redis工厂，默认使用lettue
     */
    @Autowired
    public RedisConnectionFactory redisConnectionFactory;
	
	/**
	 * 在spring5.0之后，springsecurity client密码 和 user密码必须用 PasswordEncoder 加密
	 * @return
	 */
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
    	return new BCryptPasswordEncoder();
    }
    
    
    
    /**
     * 自定义 用户表和权限表 就需要自己实现一下三个接口：
     * 		UserDetailsService 参考:org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
     * 		UserDetails 参考：org.springframework.security.core.userdetails.User
     *      GrantedAuthority 参考:org.springframework.security.core.authority.SimpleGrantedAuthority
     *      
     * 以下使用springsecurity定义的用户信息表
     * 
     * @param dataSource
     * @return
     */
    @Bean
    UserDetailsService userDetails(@Autowired DataSource dataSource){
    	JdbcDaoImpl dao = new JdbcDaoImpl();
    	dao.setDataSource(dataSource);
        return dao;
    }
	
    

    /**
     * 自定义客户端表实现以下接口：
     * 		ClientDetailsService 参考：org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
     * 
     * 
     * 以下使用springsecurity定义的客户端表
     * 
     * @param dataSource
     * @return
     */
    @Bean
    @Primary
    ClientDetailsService customClientDetailsService(@Autowired DataSource dataSource) { 
    	return new JdbcClientDetailsService(dataSource);
    }
    

    /**
     * 认证服务tokenService
     * 
     * DefaultTokenServices 实现了 AuthorizationServerTokenServices, ResourceServerTokenServices接口
     * 			AuthorizationServerTokenServices：授权服务器使用
     * 			ResourceServerTokenServices：资源服务器使用
     * @return
     */
    @Bean
    @Primary
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //配置token存储
        tokenServices.setTokenStore(tokenStore());
        //开启支持refresh_token，此处如果之前没有配置，启动服务后再配置重启服务，可能会导致不返回token的问题，解决方式：清除redis对应token存储
        tokenServices.setSupportRefreshToken(true);
        //复用refresh_token
        tokenServices.setReuseRefreshToken(true);
        //token有效期，设置1小时
        tokenServices.setAccessTokenValiditySeconds(1 * 60 * 60);
        //refresh_token有效期，设置一周
        tokenServices.setRefreshTokenValiditySeconds(7 * 24 * 60 * 60);
        return tokenServices;
    }
    
    public TokenStore tokenStore() {
        //使用redis存储token
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        //设置redis token存储中的前缀
        redisTokenStore.setPrefix("auth-token:");
        return redisTokenStore;
    }
    
    
    
    
}
