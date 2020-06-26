package com.merlin.springcloud.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.merlin.springcloud.entities.Dept;

/**
 *
 * FeignClient中配置服务降级(配置文件中启动Feign->Hystrix)
 */
//@FeignClient(value = "dept-provider")
//@FeignClient(name="dept-provider", contextId = "demo")
//@FeignClient(name = "dept-provider", configuration = FeignSupportConfig.class)
@FeignClient(value = "dept-provider", fallbackFactory = DeptClientServiceFallbackFactory.class)
public interface DeptClientService {
    // NOTE: 参数必须与Provider一致
    //  @GetMapping("/dept/list")
    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    Dept get(@PathVariable("id") Long id);

    /**
     * NOTE：Method要和Product服务提供的接口一致
     * @return
     */
    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    List<Dept> list();

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    boolean add(Dept dept);
}