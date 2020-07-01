package com.merlin.patterns.aop.cglib;

import net.sf.cglib.proxy.*;
import net.sf.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Method;

/**
 * 创建代理类(通过cglib jar包实现)
 * Interceptor: 拦截器
 */
public class CglibProxy2 implements MethodInterceptor {

    /**
     * 被代理的对象
     */
    private Object target;

    public CglibProxy2(Object target) {
        this.target = target;
    }

    //生成代理对象
    public Object createCgLibProxy() {
        // NOTE: 将class 文件保存
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./sandbox/target");

        //创建加强器，用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        //为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        enhancer.setSuperclass(target.getClass());

        // 设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(this);
        // 创建动态代理类对象并返回
        return enhancer.create();
    }

    /**
     * 实现回调方法
     *
     * @param obj    代理的对象
     * @param method 被代理对象的方法
     * @param args   参数集合
     * @param proxy  生成的代理类的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // before: 开始执行时间
        Long startTime = System.currentTimeMillis();
        // 调用业务类（父类中）的方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("Object Name: " + obj.getClass().getSimpleName());
        System.out.println("Method Name: " + method.getName());
        System.out.println("MethodProxy's SuperName: " + proxy.getSuperName());
        // after: 执行结束
        Long endTime = System.currentTimeMillis();
        System.out.println(target.getClass().getName() + "执行executeSql耗时" + (endTime - startTime) + "ms");
        return result;
    }


}



