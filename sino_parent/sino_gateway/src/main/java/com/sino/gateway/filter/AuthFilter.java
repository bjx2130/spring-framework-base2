package com.sino.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * @author <a href="mailto:zhouyanjie666666@gmail.com">zyj</a>
 * @date 2019/4/3
 */

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String PRE_FIX = "auth-token:";


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	//网关统一鉴权微服务权限，access-token不合法以json形式返回
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (token != null && token.toUpperCase().startsWith("Bearer ".toUpperCase())) {
            String accessToken = token.substring(7);
            //根据 accessToken 获取 jwtToken, 如果没有，则表示已经过期
            String redisToken = stringRedisTemplate.opsForValue().get(PRE_FIX + accessToken);
            if(redisToken==null) {
            	ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }else {
            	//透传token
                ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders -> {
                    httpHeaders.setBearerAuth(redisToken);
                }).build();
                ServerWebExchange build = exchange.mutate().request(host).build();
                return chain.filter(build);
            }
            
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
    
    
    /**
     * 	通过code 换取Access-token
     * @param code
     * @return
     */
    public String exchangeAccessToken(String code) {
        //
//    	MultiValueMap<String, String> qparams = request.getQueryParams();
//    	String code = qparams.getFirst("code");
//    	if(StringUtils.hasText(code)) {
//    	    String authorization=String.format("Basic %s", Base64Utils.encodeToString("rs1:secret".getBytes()));
//    		String url = String.format("http://localhost:7777/sino-auth/oauth/token?code=%s&grant_type=authorization_code&redirect_uri=http://localhost:7777/sino-resource1/resource&scope=all"
//    								,code);
//    		HttpHeaders headers = new HttpHeaders();
//    		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//    		headers.add("Authorization", authorization);
//    		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
//    		HttpEntity requestEntity = new HttpEntity(params,headers);
//    		ResponseEntity<String> rspBody = this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//    		System.out.println(">>>>>>>>>>>>>>>返回结果："+rspBody.getBody());
//    	}
    	return null;
    }
    
    
}