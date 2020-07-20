package com.sinoframework.web.servlet.argumentResolver;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

public interface SortArgumentResolver extends HandlerMethodArgumentResolver {

	/**
	 * Resolves a {@link Sort} method parameter into an argument value from a given request.
	 * 
	 * @param parameter the method parameter to resolve. This parameter must have previously been passed to
	 *          {@link #supportsParameter} which must have returned {@code true}.
	 * @param mavContainer the ModelAndViewContainer for the current request
	 * @param webRequest the current request
	 * @param binderFactory a factory for creating {@link WebDataBinder} instances
	 * @return the resolved argument value, or {@code null}
	 */
	@Override
	List<OrderItem> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory);
}