package com.merlin.springcloud.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.Feign;
import feign.Target;
import feign.hystrix.SetterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Feign开启Hystrix后如何配置线程隔离及熔断策略:
 * 开启Hystrix后feign之间的方法调用就会默认启动新的线程执行和主程序不在一个线程中，因此如果上下文中存在ThreadLocal变量，在该方法中就失效了。因此一般可以通过设置CommandProperties注解属性，设置线程即可。
 * REF: https://blog.csdn.net/M283592338/article/details/105133548
 */
@Configuration
public class FeignSupportConfig {
    @Bean
    public SetterFactory setterFactory(){
        SetterFactory setterFactory = new SetterFactory() {
            @Override
            public HystrixCommand.Setter create(Target<?> target, Method method) {

                String groupKey = target.name();
                String commandKey = Feign.configKey(target.type(), method);

                HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter()
                        //设置统计指标60秒为一个时间窗口
                        .withMetricsRollingStatisticalWindowInMilliseconds(1000 * 60)
                        //超过80%失败率
                        .withCircuitBreakerErrorThresholdPercentage(80)
                        //操作5个开启短路器
                        .withCircuitBreakerRequestVolumeThreshold(5)
                        //设置线程隔离
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        //设置断路器的开启时间为60秒
                        .withCircuitBreakerSleepWindowInMilliseconds(1000 * 60);

                return HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                        .andCommandPropertiesDefaults(setter);
            }
        };
        return setterFactory;
    }

}
