package com.merlin.patterns.aop.cglib;

import net.sf.cglib.proxy.*;

import java.io.IOException;

/**
 * 创建一个Test
 */
public class DemoTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        // NOTE: 将class 文件保存
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, ".");

        DemoService demoService = new DemoService();
        DemoFacadeCglib demoFacadeCglib = new DemoFacadeCglib(demoService);

        //创建加强器，用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        //为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        enhancer.setSuperclass(demoService.getClass());

        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(demoFacadeCglib);
        // 创建动态代理类对象并返回
        DemoService demoServiceProxy = (DemoService)enhancer.create();
        // 调用
        demoServiceProxy.executeMethod1();

    }
}
