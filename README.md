# spring-framework-base
快速搭建spring-boot项目，只需引入sino-framework 就会引入一些常用配置，【sino_framework，sino_common 是独立模块】
	使用spring版本
				<parent>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-parent</artifactId>
					<version>2.2.2.RELEASE</version>
					<relativePath />
				</parent>
				
				
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-dependencies</artifactId>
					<version>Hoxton.RELEASE</version>
					<type>pom</type>
					<scope>import</scope>
				</dependency>
	

### sino_framework是核心配置
      参数处理器：@RequstParam LocalDateTime
                 @RequstParam LocalDate
                 @RequstParam LocalTime
                 @RequstParam List<?>
		 	Page<Product> page [分页参数处理器]
     ObjectMapper类日期序列化模块：【jdk1.8 时间】序列化模块 SimpleModule
     全局返回值处理：ResponseControllerAdvice
     全局异常处理：SinoGlobalExceptionHandler
     RestTemplate配置：
                 开启restTemplate负载均衡，支持ip、域名、服务名 调用
                 替换StringHttpMessageConverter编码为utf-8
      添加ThreadLocal类实现：ContextHolder【ContextHolder设置值后，线程执行完成后ClearContextHolderInterceptor会清除变量】     
      fegin调用全局异常处理：FeginExceptionErrorDecoder
      bean包下定义了rest接口返回数据的包装类
      Mybatis配置：驼峰命名规则自动转换
      MyBatis-Plus： 分页插件           
   
   
### sino_common是多个模块共同VO类
### sino_gateway微服务网关
### sino_model 新建微服务demo模块【复制一个模块就可以】
	包含了 MyBatis-Plus 一些测试方法
	
### sino_test【常用的框架集成demo】

### sino_oauth2【springSecurity认证服务器】
### sino_resource【springSecurity资源服务器】
### sino_ssoclient1【springSecurity单点登录子系统1】
### sino_ssoclient2【springSecurity单点登录子系统2】
	
