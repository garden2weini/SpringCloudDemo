package com.merlin.springcloud.controller;

import com.merlin.springcloud.entities.Dept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalController {
    @GetMapping("/local/dept/get/{id}")
    public Dept localGet(@PathVariable Long id) {
        Dept dept = new Dept("ID：" + id + ". Direct to local interface!");
        dept.setDeptNo(id);
        dept.setDbSource("not use database");
        return dept;
    }
}
