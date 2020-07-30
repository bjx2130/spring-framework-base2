package com.sino.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
 * @author Administrator
 *
 */
@Controller
public class DegradeController {
	
	private static final Logger log = LogManager.getLogger();
	

	/**
	 * 	平均响应时间(RT)是先计算前5次的请求的平均处理时间，如果超过了预定的阈值时间(count)，那么在接下来的时间范围/窗口(timeWindow)后直接进行服务降级，抛出DegradeException. 等待timeWindow过后，又会重新计算RT
	 * 	
	 * 	平均响应时间 (DEGRADE_GRADE_RT)：当 1s 内持续进入 N 个请求，对应时刻的平均响应时间（秒级）均超过阈值（count，以 ms 为单位），
	 * 								那么在接下的时间窗口（DegradeRule 中的 timeWindow，以 s 为单位）之内，对这个方法的调用
	 * 								都会自动地熔断（抛出 DegradeException）。注意 Sentinel 默认统计的 RT 上限是 4900 ms，
	 * 								超出此阈值的都会算作 4900 ms，若需要变更此上限可以通过启动配置项 -Dcsp.sentinel.statistic.max.rt=xxx 来配置。
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="testRt")
	public String testRt() throws Exception {
		Thread.sleep(4000);
		return "响应时间";
	}
	
	
	/**
	 * 	一秒内要保证访问数量超过5次，否则不会出发异常比例的服务熔断降级。比例超过预定的0.2之后就会触发降级
	 * 
	 * 	异常比例 (DEGRADE_GRADE_EXCEPTION_RATIO)：当资源的每秒请求量 >= N（可配置），并且每秒异常总数占通过量的比值超过阈值
	 * 										（DegradeRule 中的 count）之后，资源进入降级状态，即在接下的时间窗口
	 * 										（DegradeRule 中的 timeWindow，以 s 为单位）之内，对这个方法的调用都会自动地返回。
	 * 										异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%。
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="testExpRatio",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String testExpRatio() throws Exception {
		
		throw new RuntimeException("跑出异常");
	}	
	
	
	/**
	 * 	异常数是统计1分钟时间的内，所以当时间窗口包含在统计时间内，可能一分钟之后又会立即进入统计时间范围中 。
	 * 
	 * 	异常数 (DEGRADE_GRADE_EXCEPTION_COUNT)：当资源近 1 分钟的异常数目超过阈值之后会进行熔断。
	 * 										注意由于统计时间窗口是分钟级别的，若 timeWindow 小于 60s，
	 * 										则结束熔断状态后仍可能再进入熔断状态。
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="testExpCount",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String testExpCount() throws Exception {
		Thread.sleep(10000);
		return "异常比例";
	}

}
