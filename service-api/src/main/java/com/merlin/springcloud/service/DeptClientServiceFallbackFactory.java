package com.merlin.springcloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.merlin.springcloud.entities.Dept;

import feign.hystrix.FallbackFactory;

/**
 * NOTE：Feign中配置服务降级的业务逻辑
 */
@Component // 不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public Dept get(Long id) {
                Dept dept = new Dept("该ID：" + id + "没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭");
                dept.setDeptNo(id);
                dept.setDbSource("no this database in MySQL");
                return dept;
            }

            @Override
            public List<Dept> list() {
                List<Dept> list = new ArrayList<>();
                Dept dept = new Dept();
                dept.setDbSource("errorSrc");
                dept.setDeptName("erorName");
                dept.setDeptNo((long) -1);
                list.add(dept);
                return list;
            }

            @Override
            public boolean add(Dept dept) {
                return false;
            }
        };
    }
}
