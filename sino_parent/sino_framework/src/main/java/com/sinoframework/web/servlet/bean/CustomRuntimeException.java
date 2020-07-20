package com.sinoframework.web.servlet.bean;

public class CustomRuntimeException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4305195497124457405L;

	private String msg;

	public CustomRuntimeException(String message) {
        super(message);
        this.msg = message;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String message) {
		this.msg = message;
	}
	
}
