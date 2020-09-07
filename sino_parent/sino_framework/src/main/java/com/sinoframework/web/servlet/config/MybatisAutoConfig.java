package com.sinoframework.web.servlet.config;

import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;

@Configuration
public class MybatisAutoConfig {
	
	private static final Logger log = LoggerFactory.getLogger(MybatisAutoConfig.class);
	
	/**
	 * 	注意：如果配置 mybatis-plus.configLocation 【会导致ConfigurationCustomizer接口的实现失效】
	 * 
	 * 		#  扫描数据持久对应的实体类 路径 Package级别
	 *		mybatis-plus.type-aliases-package=com.sino.llb.entity;com.sino.llb.vo
	 *		#mybatis 对应的 .xml文件路径
	 *		mybatis-plus.mapper-locations=classpath:com/sino/llb/dao/*.xml
	 * 
	 * 
	 *  	配置文档参考：https://mp.baomidou.com/config/#%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE
	 * @param mybatisPro
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "sino.mybatisConfigurationCustomizer", value = "enabled", matchIfMissing = true)
	public ConfigurationCustomizer mybatisConfigurationCustomizer(@Autowired MybatisPlusProperties mybatisPro){
		//
		if(mybatisPro.getConfigLocation()!=null) {
			return null;
		}
		
		log.info("启用：mybatis 驼峰命名规则自动转换");
		log.info("启用：mybatis MybatisMapWrapper包装类");
		/** 此处配置优先级高于properties中的配置*/
		return (configuration)->{
			
			//默认配置类 MybatisXMLConfigBuilder
			configuration.setCallSettersOnNulls(true);
			configuration.setJdbcTypeForNull(JdbcType.NULL);//oracle数据库需配置JdbcType.NULL, 默认是Other
        	configuration.setMapUnderscoreToCamelCase(true);//设置驼峰命名规则  ,默认是true
        	configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
		};
	}
	
	
	/**
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "sino.paginationInterceptor", value = "enabled", matchIfMissing = true)
    public PaginationInterceptor paginationInterceptor() {
		log.info("启用：MyBatis-Plus 分页插件");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
	
	
}
