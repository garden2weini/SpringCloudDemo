package com.merlin.patterns.aop.cglib;

/**
 * 创建一个需要被代理的类
 */
public class DemoService {
    public void executeMethod1() throws InterruptedException {
        System.out.println("Sql 开始执行.....");
        Thread.sleep(1000);
        System.out.println("Sql 执行结束.....");
    }
}
