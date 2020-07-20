package com.sinoframework.web.servlet.interceptor.resttemplate;

import java.io.IOException;
import java.net.URI;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.PriorityOrdered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.http.client.*;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

/**
 * @LoadBalanced 支持ip、域名、服务名 调用
 * 	如果想支持其他的域名格式，可以在application.properties中增加配置项
	mix.loadbalancer.additional.regex=.*\\.(int|mil)
	
 * @author Administrator
 *
 */
public class MixLoadBalancerInterceptor implements ClientHttpRequestInterceptor, PriorityOrdered {
 
    @Value("${mix.loadbalancer.ip.regex:(((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)|localhost)}")
    private String ipRegex;
 
//    @Value("${mix.loadbalancer.domain.regex:.*\\.(com|xyz|net|top|tech|org|gov|edu|pub|cn|biz|cc|tv|info|im)}")
//    private String domainRegex;
 
    @Value("${mix.loadbalancer.additional.regex:}")
    private String additionalRegex;
 
 
    private ClientHttpRequestFactory httpRequestFactory;
 
    public MixLoadBalancerInterceptor(ClientHttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }
 
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final URI originalUri = request.getURI();
        String serviceName = originalUri.getHost();
        Assert.state(serviceName != null, "Request URI does not contain a valid hostname: " + originalUri);
        // 判断是否为ip、域名
        if (serviceName.matches(String.format("^%s|%s$", ipRegex, additionalRegex))) {
            // 实际http请求，这段拷贝于：InterceptingClientHttpRequest
            HttpMethod method = request.getMethod();
            Assert.state(method != null, "No standard HTTP method");
            ClientHttpRequest delegate = httpRequestFactory.createRequest(request.getURI(), method);
            request.getHeaders().forEach((key, value) -> delegate.getHeaders().addAll(key, value));
            if (body.length > 0) {
                if (delegate instanceof StreamingHttpOutputMessage) {
                    StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) delegate;
                    streamingOutputMessage.setBody(outputStream -> StreamUtils.copy(body, outputStream));
                }
                else {
                    StreamUtils.copy(body, delegate.getBody());
                }
            }
            return delegate.execute();
        }
        else {
            // 如果非ip、域名，继续走@LoadBalanced的原逻辑
            return execution.execute(request, body);
        }
    }
 
    @Override
    public int getOrder() {
        return 0;
    }

}
