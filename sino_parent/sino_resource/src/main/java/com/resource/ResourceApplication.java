package com.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class ResourceApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ResourceApplication.class, args);
    }
}
