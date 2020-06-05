package com.merlin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 7001
 */
@SpringBootApplication //springBoot启动类必需注解
@EnableEurekaServer // EurekaServer服务器端启动类,接受其它微服务注册进来
public class EurekaServerApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(EurekaServerApp.class, args);
    }
}