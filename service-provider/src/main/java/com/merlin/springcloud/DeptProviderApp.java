package com.merlin.springcloud;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
// import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * NOTE: 从Spring Cloud Edgware开始，@EnableDiscoveryClient 或@EnableEurekaClient 可省略。只需加上相关依赖，并进行相应配置，即可将微服务注册到服务发现组件上。
 * NOTE: 在启动类中使用 @EnableSwagger2Doc 开启 Swagger
 */
@SpringBootApplication
@EnableSwagger2Doc
//@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
//@EnableDiscoveryClient //服务发现, 可注册到所有类型的注册中心
public class DeptProviderApp {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(DeptProviderApp.class, args);
    }
}