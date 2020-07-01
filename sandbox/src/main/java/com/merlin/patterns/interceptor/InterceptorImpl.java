package com.merlin.patterns.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InterceptorImpl implements Interceptor {
    @Override
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("反射方法前逻辑");
        return false;    //这里为false，则不调用目标原有方法，直接调用around的替代方法
    }

    @Override
    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("反射方法后逻辑");
    }

    @Override
    public void around(Object proxy, Object target, Method method, Object[] args) {
        // NOTE：如果before中逻辑阻断，在这里可以控制不继续执行！
        System.out.println("...around方法可以控制被代理对象的方法是否被执行！");

        // 执行原有的业务逻辑代码
        try {
            method.invoke(target, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}