/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinoframework.web.servlet.argumentResolver;


import java.awt.print.Pageable;
import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 测试分页地址 http://localhost:9091/page?page=0&size=10&sort=pdType,asc&sort=pdTitle,desc
 * 											  size<0  不分页
 * 
 * 
 * 参考SortHandlerMethodArgumentResolver源码实现。
 * 	默认参数规则为 sort=name,DESC
 * 	name： 表示要排序的字段名称
 * 	DESC 、 ASC: 正序和倒序
 * 	排序默认为ASC
 * 	sort=name
 * 	sort=name,DESC
 * 	sort=name1,DESC&sort=name2 表示同时排序多个字段
 * 
 * @param page
 * @return
 */
public class PageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static final String INVALID_DEFAULT_PAGE_SIZE = "Invalid default page size configured for method %s! Must not be less than one!";

	private static final String DEFAULT_PAGE_PARAMETER = "page";
	private static final String DEFAULT_SIZE_PARAMETER = "size";
	private static final String DEFAULT_PREFIX = "";
	private static final String DEFAULT_QUALIFIER_DELIMITER = "_";
	private static final int DEFAULT_MAX_PAGE_SIZE = 2000;
	static final IPage DEFAULT_PAGE_REQUEST = new Page(1, 20);

	private IPage fallbackPageable = DEFAULT_PAGE_REQUEST;
	private String pageParameterName = DEFAULT_PAGE_PARAMETER;
	private String sizeParameterName = DEFAULT_SIZE_PARAMETER;
	
	private SortArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver(true);
	
	private String prefix = DEFAULT_PREFIX;
	private String qualifierDelimiter = DEFAULT_QUALIFIER_DELIMITER;
	private int maxPageSize = DEFAULT_MAX_PAGE_SIZE;
	private boolean oneIndexedParameters = false;
	
	
	


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Page.class.equals(parameter.getParameterType());
	}

	@Override
	public IPage resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		IPage<?> defaultOrFallback = DEFAULT_PAGE_REQUEST;
		
		Page<?> _page = new Page(0,-1);
		
		String pageString = webRequest.getParameter(getParameterNameToUse(pageParameterName, methodParameter));
		String pageSizeString = webRequest.getParameter(getParameterNameToUse(sizeParameterName, methodParameter));

		boolean pageAndSizeGiven = StringUtils.hasText(pageString) && StringUtils.hasText(pageSizeString);
		List<OrderItem> sortList = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
		
		
		if (!pageAndSizeGiven && sortList.size()==0) {
			return null;
		}

		if(pageAndSizeGiven) {
			long page = StringUtils.hasText(pageString) ? parseAndApplyBoundaries(pageString, Integer.MAX_VALUE, true)
					: defaultOrFallback.getCurrent();
			long pageSize = StringUtils.hasText(pageSizeString) ? parseAndApplyBoundaries(pageSizeString, maxPageSize, false)
					: defaultOrFallback.getSize();
			pageSize = pageSize > maxPageSize ? maxPageSize : pageSize;
			
			_page.setPages(page);
			_page.setSize(pageSize);
		}
		if(sortList.size()>0) {
			_page.addOrder(sortList);
		}
		return _page;
	}

	protected String getParameterNameToUse(String source, MethodParameter parameter) {

		StringBuilder builder = new StringBuilder(prefix);

		if (parameter != null && parameter.hasParameterAnnotation(Qualifier.class)) {
			builder.append(parameter.getParameterAnnotation(Qualifier.class).value());
			builder.append(qualifierDelimiter);
		}

		return builder.append(source).toString();
	}

	private IPage getDefaultFromAnnotationOrFallback(MethodParameter methodParameter) {
		return fallbackPageable;
	}


	private int parseAndApplyBoundaries(String parameter, int upper, boolean shiftIndex) {

		try {
			return Integer.parseInt(parameter);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
