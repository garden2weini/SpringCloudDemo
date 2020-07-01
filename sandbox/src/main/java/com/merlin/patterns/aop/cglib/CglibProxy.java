package com.merlin.patterns.aop.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 通过spring-core的jar包实现
 * CGLIB动态代理
 * 一：CGLIB（Code Generation Library)是一个基于ASM的字节码生成库，它允许我们在运行时对字节码进行修改和动态生成。CGLIB通过继承方式实现代理。
 * 二：使用cglib需要引入cglib的jar包，如果你已经有spring-core的jar包，则无需引入，因为spring中包含了cglib。
 * 三：cglib代理无需实现接口，通过生成类字节码实现代理，比反射稍快，不存在性能问题，但cglib会继承目标对象，需要重写方法，所以目标对象不能为final类
 */
public class CglibProxy implements MethodInterceptor {

    //保存被代理的对象
    private Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    //生成代理对象
    public Object createCgLibProxy() {
        //工具类
        Enhancer enhancer = new Enhancer();
        //设置被代理的对象，也可以理解为设置父类，因为动态代理类是继承了被动态代理类。
        enhancer.setSuperclass(target.getClass());
        //设置回调函数
        enhancer.setCallback(this);
        //创建子类的动态代理类对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("01：打开冰箱门~~~~");
        method.invoke(target, objects);
        System.out.println("03：关闭冰箱门~~~~");
        return null;
    }

}