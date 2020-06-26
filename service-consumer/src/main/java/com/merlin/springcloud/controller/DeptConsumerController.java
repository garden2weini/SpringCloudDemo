package com.merlin.springcloud.controller;

import com.merlin.springcloud.entities.Dept;
import com.merlin.springcloud.service.DeptClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 服务消费端
 *
 * @Version: 1.0
 * @Author: Merlin
 *
 */
@RestController
public class DeptConsumerController {
    private Logger logger = LoggerFactory.getLogger(DeptConsumerController.class);

    @Autowired
    private DeptClientService deptClient;

    // NOTE：（绕过Eureka）直接访问Provider Rest接口
    //private static final String REST_URL_PREFIX = "http://localhost:8001";
    // NOTE：通过RestTemplate访问Eureka中的Provider
    private static final String REST_URL_PREFIX = "http://dept-provider";

    /**
     * 使用 使用restTemplate访问restful接口非常的简单粗暴无脑。 (url, requestMap,
     * ResponseBean.class)这三个参数分别代表 REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
     */
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/consumer/dept/add")
    public boolean add(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Boolean.class);
    }

    @RequestMapping(value = "/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id, Dept.class);
    }

    /**
     * NOTE: HystrixCommand(fallbackMethod)设置服务降级
     * @return
     */
    @RequestMapping(value = "/consumer/dept/listx")
    public List<Dept> list4Feign() {
        // NOTE：通过Feign获取服务接口
        return deptClient.list();
    }

    @RequestMapping(value = "/consumer/dept/getx/{id}")
    public Dept get4Feign(@PathVariable("id") Long id) {
        // NOTE：通过Feign获取服务接口
        return deptClient.get(id);
    }

    @RequestMapping(value = "/consumer/dept/list")
    public List<Dept> list() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/list", List.class);
    }

    // 测试@EnableDiscoveryClient,消费端可以调用服务发现
    @RequestMapping(value = "/consumer/dept/discovery")
    public Object discovery() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/discovery", Object.class);
    }
}