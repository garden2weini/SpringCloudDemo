package com.merlin.patterns.aop.cglib;

/**
 * 创建一个需要被代理的类
 */
public class Demo2Service {
    public void executeMethod21() throws InterruptedException {
        System.out.println("Demo2 开始执行.....");
        Thread.sleep(200);
        System.out.println("Demo2 执行结束.....");
    }

    public void executeMethod22() throws InterruptedException {
        System.out.println("Demo2 开始执行.....");
        Thread.sleep(200);
        System.out.println("Demo2 执行结束.....");
    }
}
