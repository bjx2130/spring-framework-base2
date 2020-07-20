package com.example.oauth.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;


@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfigurer extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	private AuthorizationServerTokenServices defaultTokenServices;
	
	
	/**
	 * 	用来配置客户端详情服务
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//==========使用内存储客户端信息=============
//		clients.inMemory() // 使用in-memory存储
//				.withClient("client") // client_id
//				.secret(bcryptPasswordEncoder.encode("secret")) // client_secret
//				.authorizedGrantTypes("authorization_code") // 该client允许的授权类型
//				// .autoApprove(true) //登录后绕过批准询问(/oauth/confirm_access)
//				.redirectUris("http://www.baidu.com")
//				.scopes("app"); // 允许的授权范围
		
		//==========使用数据库存储客户端信息=============
		clients.withClientDetails(clientDetailsService);
		
	}

	
	/**
	 * 	用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)；
	 */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置认证管理器
        endpoints
                //配置用户服务
                .userDetailsService(userDetailsService)
                //配置token存储的服务与位置
                .tokenServices(this.defaultTokenServices);
    }
	
	
    
	/**
	 * 	用来配置令牌端点(Token Endpoint)的安全约束；
	 * 
	 * =======可选配置=========
	 *	 资源服务器与认证服务器分离时
	 * 	例如资源服务器 ResourceServerTokenServices 的  RemoteTokenServices 实现类时
	 * 	需要授权资源服务器权限：http://localhost:8888/oauth/check_token
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients();
		
	}

}

