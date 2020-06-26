package com.merlin.patterns.aop.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class SpringAopDemoApp {
    private Logger logger = LoggerFactory.getLogger(SpringAopDemoApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringAopDemoApp.class, args);
    }

    @LogPrint(desc = "属性描述")
    @GetMapping(value = "/toIndex")
    public ModelAndView toIndex(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return(mav);
    }

    @LogPrint(desc = "Hello, 属性值")
    @GetMapping(value = "/test")
    public String test(){
        return "Test, ok?";
    }

}
