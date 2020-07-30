package com.sinoframework.web.servlet.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinoframework.web.servlet.bean.ResponseResult;

/**
 * 仅支持带有 @ResponseBody 的方法
 * @author Administrator
 *
 */
@RestControllerAdvice // 注意哦，这里要加上需要扫描的包
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
	private static final Logger log = LoggerFactory.getLogger(ResponseControllerAdvice.class);
	
	public ResponseControllerAdvice() {
    	log.info("启用【接口统一返回增强类 ResponseControllerAdvice】");
	}
	
	
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        return !(returnType.getGenericParameterType().equals(ResponseResult.class)||returnType.getGenericParameterType().equals(ResponseEntity.class));
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(new ResponseResult<>(data));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("返回String类型错误");
            }
        }
        // 将原本的数据包装在ResultVO里
        return new ResponseResult<>(data);
    }
}