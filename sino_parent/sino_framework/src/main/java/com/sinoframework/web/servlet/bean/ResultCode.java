package com.sinoframework.web.servlet.bean;
public enum ResultCode{
	
    SUCCESS(2000, "操作成功"),


    ERROR(5000, "未知错误"),
    
    EXTITER_API_ERROR(5003, "外部接口异常"),
	
	FAIL(4003,"拒绝请求");
	
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
    
}