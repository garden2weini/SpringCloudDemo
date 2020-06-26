package com.merlin.patterns.aop.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * 创建代理类
 */
public class DemoFacadeCglib implements MethodInterceptor {

    /**
     * 被代理的对象
     */
    private Object target;

    public DemoFacadeCglib(Object target) {
        this.target = target;
    }

    /**
     *  实现回调方法
     * @param obj 代理的对象
     * @param method 被代理对象的方法
     * @param args  参数集合
     * @param proxy 生成的代理类的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // before: 开始执行时间
        Long startTime = System.currentTimeMillis();
        // 调用业务类（父类中）的方法
        Object result = proxy.invokeSuper(obj, args);
        // after: 执行结束
        Long endTime = System.currentTimeMillis();
        System.out.println(target.getClass().getName()+"执行executeSql耗时"+(endTime-startTime)+"ms");
        return result;
    }


}