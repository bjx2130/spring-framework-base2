package com.sino.fallback;


/**
 * fallback 函数签名和位置要求：
			1、返回值类型必须与原函数返回值类型一致；
			2、方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
			3、fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
 * @param str
 * @return
 */
public class FallbackHandler {

	public static String fallbackHandler(String str) {
		 return "扛不住了啊....";
   }
}
