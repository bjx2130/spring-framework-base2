package com.sinoframework.web.servlet.argumentResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

public class ListArgumentResolver extends AbstractNamedValueMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if(parameter.getGenericParameterType() instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType)parameter.getGenericParameterType();
			if(parameterizedType.getRawType().equals(List.class))
					return true;
		}
		return false;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		RequestParam ann = parameter.getParameterAnnotation(RequestParam.class);
		return (ann != null ? new RequestParamNamedValueInfo(ann) : new RequestParamNamedValueInfo());
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		String[] paramValues = request.getParameterValues(name);
		String[] els = paramValues[0].split(",");
    	
    	ParameterizedType parameterizedType = (ParameterizedType)parameter.getGenericParameterType();
    	Type type = parameterizedType.getActualTypeArguments()[0];
    	
    	List<Object> list= new ArrayList<Object>();
    	for (int i = 0; i < els.length; i++) {
			list.add(this.castParamterVal(els[i], type));
		}
        return list;
	}
	
	
	private Object castParamterVal(String val,Type type) {
    	if(type.equals(String.class)) {
    		return val;
    	}else if(type.equals(Integer.class)){
    		return Integer.parseInt(val);
    	}else {
    		throw new RuntimeException("转换失败");
    	}
	}
	
	private static class RequestParamNamedValueInfo extends NamedValueInfo {

		public RequestParamNamedValueInfo() {
			super("", false, ValueConstants.DEFAULT_NONE);
		}

		public RequestParamNamedValueInfo(RequestParam annotation) {
			super(annotation.name(), annotation.required(), annotation.defaultValue());
		}
	}
}
