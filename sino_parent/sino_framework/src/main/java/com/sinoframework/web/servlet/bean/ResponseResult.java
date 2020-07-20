package com.sinoframework.web.servlet.bean;


public class ResponseResult<T>  {
	
	 /**
     * 状态码，比如1000代表响应成功
     */
    public int code; //返回状态码200成功
    
    /**
     * 响应信息，用来说明响应情况
     */
    private String msg; //返回描述信息
 
    private T data; //返回内容体	
	
    public ResponseResult(T data) {
        this(ResultCode.SUCCESS, data);
    }
    
    public ResponseResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
    
    
    public ResponseResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
    
    
//    public ResponseResult<T> setCode(ResultCode resultCode) {
//        this.code = resultCode.getCode();
//        this.msg = resultCode.getMsg();
//        return this;
//    }
 
    public int getCode() {
        return code;
    }
 
 
    public String getMsg() {
        return msg;
    }
 
    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
 
    public T getData() {
        return data;
    }
 
    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }	
	
    
    


}
