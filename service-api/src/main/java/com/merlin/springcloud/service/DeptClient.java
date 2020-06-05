package com.merlin.springcloud.service;

import com.merlin.springcloud.entities.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Deprecated
@FeignClient(name="dept-provider", contextId = "demo")
public interface DeptClient {

    @GetMapping("/dept/list")  // 这里要和 Product服务提供的接口一致
    List<Dept> list();

    // NOTE: 参数必须与Provider一致
    @GetMapping(value = "/dept/get/{id}")
    Dept get(@PathVariable("id") Long id);
}
