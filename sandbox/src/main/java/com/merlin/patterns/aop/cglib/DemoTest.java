package com.merlin.patterns.aop.cglib;

/**
 * 测试raw CGLib和SpringCore cglib构造代理类
 */
public class DemoTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("To proxy for raw cglib jar......");
        testRawCglib();
        System.out.println("To proxy for raw spring-core(cglib) jar......");
        testSpringCglib();
    }

    private static void testSpringCglib() throws InterruptedException {
        //新建被代理类的对象
        Demo1Service cglibPractice = new Demo1Service();
        //生成代理类对象
        Demo1Service cglibPracticeProxy = (Demo1Service) new CglibProxy(cglibPractice).createCgLibProxy();
        cglibPracticeProxy.executeMethod13();
        //控制台输出内容
        /**
         * 01：打开冰箱门~~~~
         * 02：把大象塞进去~~~~
         * 03：关闭冰箱门~~~~
         **/
    }

    private static void testRawCglib() throws InterruptedException {
        Demo1Service demo1Service = new Demo1Service();
        Demo2Service demo2Service = new Demo2Service();

        System.out.println("To proxy class Demo1Service...");
        CglibProxy2 cglibProxy2 = new CglibProxy2(demo1Service);
        Demo1Service demo1ServiceProxy = (Demo1Service)cglibProxy2.createCgLibProxy();
        // 调用
        demo1ServiceProxy.executeMethod11();
        demo1ServiceProxy.executeMethod12();

        System.out.println("To proxy class Demo2Service...");
        cglibProxy2 = new CglibProxy2(demo2Service);

        // 创建动态代理类对象并返回
        Demo2Service demo2ServiceProxy = (Demo2Service)cglibProxy2.createCgLibProxy();
        // 调用
        demo2ServiceProxy.executeMethod21();
        demo2ServiceProxy.executeMethod22();
    }

}
