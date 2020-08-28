package com.example.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class OauthApplication {
/**
 * 
 * 
 * 	      开启权限控制注解支持
        jsr250-annotations="enabled"表示支持jsr250-api的注解，需要jsr250-api的jar包
        pre-post-annotations="enabled"表示支持spring表达式注解
        secured-annotations="enabled"这才是SpringSecurity提供的注解
 *    1)、因为要想使用注解,所以必须开启对指定注解的支持;
                方式一：<security:global-method-securityjsr250-annotations="enabled"/>

                方式二：<security:global-method-securitypre-post-annotations="enabled"/>

                方式三：<security:global-method-securitysecured-annotations="enabled"/>

       2)、在指定的方法或类上添加指定注解;
                方式一：@RolesAllowed({角色名一，角色名二})

                方式二：@PreAuthorize({角色名一，角色名二})

                方式三：@Secured({{角色名一，角色名二}})

      说明：注解支持与相应的注解是对应的,开启哪个注解支持就使用哪个注解；

 * @param args
 */
	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
		
		/**
		 * sringsecurity访问控制列表实现参考以下链接
		 * JdbcMutableAclService
		 * https://blog.csdn.net/dongzi87/article/details/48142955
		 */
	}
}

