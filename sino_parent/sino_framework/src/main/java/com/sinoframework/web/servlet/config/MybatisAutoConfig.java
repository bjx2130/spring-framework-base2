package com.sinoframework.web.servlet.config;

import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;

@Configuration
public class MybatisAutoConfig {
	
	private static final Logger log = LoggerFactory.getLogger(MybatisAutoConfig.class);
	
	/**
	 * 注意：如果配置 mybatis.configLocation 【会导致ConfigurationCustomizer接口的实现失效】
	 * @param mybatisPro
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "sino.mybatisConfigurationCustomizer", value = "enabled", matchIfMissing = true)
	public ConfigurationCustomizer mybatisConfigurationCustomizer(@Autowired MybatisProperties mybatisPro){
		//
		if(mybatisPro.getConfigLocation()!=null) {
			return null;
		}
		
		log.info("启用：mybatis 驼峰命名规则自动转换");
		log.info("启用：mybatis MybatisMapWrapper包装类");
		return (configuration)->{
			configuration.setCallSettersOnNulls(true);
			configuration.setJdbcTypeForNull(JdbcType.NULL);
        	configuration.setMapUnderscoreToCamelCase(true);//设置驼峰命名规则  
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
