package com.sinoframework.web.servlet.bean;

/**
 * @ClassName: Response
 * @Description:将结果转换为封装后的对象
 * @Author: sunt
 * @Date: 2019/10/31 16:11
 * @Version 1.0
 **/
public class Response {
 
 
    public static <T> ResponseResult<T> makeOKRsp() {
        return new ResponseResult<T>(ResultCode.SUCCESS);
    }
    
    public static <T> ResponseResult<T> makeFailRsp(T data) {
        return new ResponseResult<T>(ResultCode.FAIL,data);
    }
    
    public static <T> ResponseResult<T> makeExtIterApiRsp() {
        return new ResponseResult<T>(ResultCode.EXTITER_API_ERROR);
    }  
    
    public static <T> ResponseResult<T> makeErrotRsp() {
        return new ResponseResult<T>(ResultCode.ERROR);
    } 
    
    public static <T> ResponseResult<T> makeUnauthorized(T data) {
        return new ResponseResult<T>(ResultCode.UNAUTHORIZED,data);
    }
    
    
    
    public static <T> ResponseResult<T> makeOKRsp(T data) {
        return new ResponseResult<T>(data);
    }
 

    

}