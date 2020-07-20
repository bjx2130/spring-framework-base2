package com.example.oauth.secutity.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

public class SmsCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private String SMS_CODE_PARAMTER= "smscode";

	public SmsCodeAuthenticationFilter() {
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String validCode = this.obtainValidCode(request);
		if(StringUtils.isEmpty(validCode)) {
			throw new UsernameNotFoundException("验证码不能为空");
		}
		if(!validCode.equals(this.getSessionSmsCode(request))) {
			throw new UsernameNotFoundException("验证码错误");
		}
		request.getSession().removeAttribute(super.obtainUsername(request));
		
		return super.attemptAuthentication(request, response);
	}

	protected String obtainValidCode(HttpServletRequest request) {
		return request.getParameter(this.SMS_CODE_PARAMTER);
	}
	
	/**
	 * 	根据用户名从session中获取短信验证码
	 * @param request
	 * @return
	 */
	protected String getSessionSmsCode(HttpServletRequest request) {
		return request.getSession().getAttribute(super.obtainUsername(request)).toString();
	}
	
}
