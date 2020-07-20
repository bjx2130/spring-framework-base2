package com.sinoframework.web.servlet.config;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.sinoframework.web.servlet.argumentResolver.ListArgumentResolver;
import com.sinoframework.web.servlet.argumentResolver.PageHandlerMethodArgumentResolver;

/**
 * 接收参数处理器
 * 	
 * 	包含【@RequstParam @ResponseBody @RequestBody】的参数转换
 * 
 * 
 */
@Configuration
public class ArgumentResolverAutoConfig  implements WebMvcConfigurer{
	private static final Logger log = LoggerFactory.getLogger(ArgumentResolverAutoConfig.class);
	
    /**
     * ObjectMapper序列化【jdk1.8 时间】 格式化{覆盖了默认配置类JacksonAutoConfiguration内部静态类JodaDateTimeJacksonConfiguration}
     * 
     * @param jacksonProperties
     * @return
     */
	@Bean
	SimpleModule jodaDateTimeSerializationModule(JacksonProperties jacksonProperties) {
		log.info("启用【ObjectMapper 【jdk1.8 时间】序列化模块 SimpleModule】");
		SimpleModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return javaTimeModule;
	}
    
    

    /**
     * 	处理 @RequestParam  
     * 		LocalDateTime 类型转换
     * 
     */
    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
    	log.info("启用【Formatter 时间参数处理器 LocalDateTime】");
    	
        return new Formatter<LocalDateTime>() {
            @Override
            public String print(LocalDateTime object, Locale locale) {
                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            @Override
            public LocalDateTime parse(String text, Locale locale) throws ParseException {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };
    }
    
    /**
     * 	处理 @RequestParam  
     * 		LocalDate 类型转换
     * 
     */
    @Bean
    public Formatter<LocalDate> localDateFormatter() {
    	log.info("启用【Formatter 时间参数处理器 LocalDate】");
        return new Formatter<LocalDate>() {
            @Override
            public String print(LocalDate object, Locale locale) {
                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }
    
    /**
     * 	处理 @RequestParam  
     * 		LocalTime 类型转换
     * 
     */
    @Bean
    public Formatter<LocalTime> localTimeFormatter() {
    	log.info("启用【Formatter 时间参数处理器 LocalTime】");
        return new Formatter<LocalTime>() {
            @Override
            public String print(LocalTime object, Locale locale) {
                return object.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            }

            @Override
            public LocalTime parse(String text, Locale locale) throws ParseException {
                return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
        };
    }
    
    
    
    
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    	log.info("启用【ListStringArgumentResolver参数处理器 处理 【@RequestListParam List<?> idList】类型参数】");
    	resolvers.add(new ListArgumentResolver());
    	log.info("启用【SortArgumentResolver参数处理器 处理 【驼峰转换成下划线:默认false】类型参数】");
    	log.info("启用【PageHandlerMethodArgumentResolver参数处理器 处理 【Page】类型参数】");
    	resolvers.add(new PageHandlerMethodArgumentResolver());
    	
	}
    
    
    

}