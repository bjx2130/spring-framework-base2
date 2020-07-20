package com.sinoframework.web.servlet.argumentResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

public class SortHandlerMethodArgumentResolver  implements SortArgumentResolver {
	private String propertyDelimiter = ",";
	private String sortParameter = "sort";
	private String qualifierDelimiter = "_";
	
	//驼峰转换成下划线
	private boolean underline2camel = false ;
	
	

	public SortHandlerMethodArgumentResolver(boolean underline2camel) {
		super();
		this.underline2camel = underline2camel;
		
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return OrderItem.class.equals(parameter.getParameterType());
	}

	@Override
	public List<OrderItem> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		
		String[] directionParameter = webRequest.getParameterValues(getSortParameter(parameter));



		return parseParameterIntoSort(directionParameter, propertyDelimiter);
	}
	
	protected String getSortParameter(MethodParameter parameter) {

		StringBuilder builder = new StringBuilder();

		if (parameter != null && parameter.hasParameterAnnotation(Qualifier.class)) {
			builder.append(parameter.getParameterAnnotation(Qualifier.class).value()).append(qualifierDelimiter);
		}

		return builder.append(sortParameter).toString();
	}
	
	private List<OrderItem> parseParameterIntoSort(String[] source, String delimiter) {
//		Configuration mybatisConfig = SpringUtil.getBean(org.apache.ibatis.session.Configuration.class);
//		underline2camel = mybatisConfig.isMapUnderscoreToCamelCase();
		
		List<OrderItem> allOrders = new ArrayList<OrderItem>();
		if(source==null) {
			return allOrders;
		}
		
		
		for (String part : source) {

			if (part == null) {
				continue;
			}
			String[] elements = part.split(delimiter);
			if("desc".equalsIgnoreCase(elements[1])) {
				allOrders.add(OrderItem.desc(this.underline2camel(elements[0], underline2camel)));
			}else if("asc".equalsIgnoreCase(elements[1])) {
				allOrders.add(OrderItem.asc(this.underline2camel(elements[0], underline2camel)));
			}
		}	
		
		
		
		return allOrders;
	}
	
	/**
	 * 驼峰转下划线
	 */
	private static Pattern humpPattern = Pattern.compile("[A-Z]");
	public static String underline2camel(String str,boolean covert) {
		if(!covert)return str;
		
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
