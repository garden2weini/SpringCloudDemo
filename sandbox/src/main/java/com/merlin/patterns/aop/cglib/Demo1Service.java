package com.merlin.patterns.aop.cglib;

public class Demo1Service {
    public void executeMethod11() throws InterruptedException {
        System.out.println("Demo1-1 开始执行.....");
        Thread.sleep(100);
        System.out.println("Demo1-1 执行结束.....");
    }

    public void executeMethod12() throws InterruptedException {
        System.out.println("Demo1-2 开始执行.....");
        Thread.sleep(100);
        System.out.println("Demo1-2 执行结束.....");
    }

    public void executeMethod13() throws InterruptedException {
        System.out.println("02：把大象塞进去~~~~");
    }
}
