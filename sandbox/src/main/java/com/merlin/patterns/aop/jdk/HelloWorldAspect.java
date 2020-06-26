package com.merlin.patterns.aop.jdk;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class HelloWorldAspect implements InvocationHandler {
    Object instance;

    public Object aspect(Object instance) {
        this.instance = instance;
        return Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(), this);
    }

    public void before(Method method, Object[] args) {
        System.out.println("~~~~ before: " + method.getName() + " " + args);
    }

    public void after(Object object) {
        System.out.println("~~~~ after: " + object);
    }

    public static void init() {
        Field[] fields = HelloWorldAspect.class.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            HelloWorldAspect myAspect = new HelloWorldAspect();
            HelloWorld aspects = fields[i].getType().getAnnotation(HelloWorld.class);
            if (aspects != null) {
                fields[i].setAccessible(true);
                try {
                    fields[i].set(myAspect, new HelloWorldAspect().aspect(fields[i].get(myAspect)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        before(method, args);
        result = method.invoke(instance, args);
        after(result);
        //return null;
        return result;
    }

    public static HelloAspect testAspect = new HelloAspectImpl();
    public static HelloAspect testAspectA = new HelloAspectImplA();

    public static void main(String[] args) {
        init();

        testAspect.test("I'm TestAspectImpl");
        testAspectA.test("I'm TestAspectImplA");
    }


}
