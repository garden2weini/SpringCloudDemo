package com.merlin.springcloud.controller;

import com.merlin.springcloud.entities.Dept;
import com.merlin.springcloud.service.DeptService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务提供端
 * NOTE: As long as Spring Boot Actuator and Spring Config Client are on the classpath any Spring Boot application will try to contact a config server on http://localhost:8888, the default value of spring.cloud.config.uri.
 *
 * @Filename: DeptController.java
 * @Version: 1.0
 * @Author: Merlin
 *
 */
@Configuration
@EnableAutoConfiguration
@RestController
public class DeptController {
    private Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService service;

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Dept dept) {
        return service.add(dept);
    }

    // Swagger注解：ApiOperation/ApiResponses
    @ApiOperation(value = "获取Dept信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Dept.class) })
    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        return service.list();
    }

    //	@Autowired
//	private DiscoveryClient client;
    @RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
    public Object discovery() {
        List<String> list = client.getServices();
        System.out.println("**********" + list);

        List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
        for (ServiceInstance element : srvList) {
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
                    + element.getUri());
        }
        return this.client;
    }

}