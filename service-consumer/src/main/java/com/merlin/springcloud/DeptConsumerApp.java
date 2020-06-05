package com.merlin.springcloud;

import com.merlin.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * NOTE: @EnableFeignClients中已经默认打开了断路器功能，所以这里的启动类上不需要再加@EnableCircuitBreaker注解
 * NOTE: 在启动该微服务的时候就能去加载我们的自定义Ribbon（负载均衡）配置类，从而使配置生效
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
//@EnableCircuitBreaker
//@RibbonClient(name="MICROSERVICECLOUD-DEPT", configuration= MySelfRule.class)
public class DeptConsumerApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(DeptConsumerApp.class, args);
    }
}