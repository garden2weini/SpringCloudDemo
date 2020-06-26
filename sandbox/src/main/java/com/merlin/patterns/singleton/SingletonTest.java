package com.merlin.patterns.singleton;

import java.lang.reflect.Constructor;

/**
 *
 */
public class SingletonTest {
    public static void main(String[] args) {
        testInnerSingleton();
    }

    public static void testEnumSingleton() {
        System.out.println(Singleton4Enum.getInstance());
        System.out.println(Singleton4Enum.getInstance());
        System.out.println(Singleton4Enum.getInstance() == Singleton4Enum.getInstance());
    }

    /**
     * 如果通过反射直接绕过访问点去找构造方法, 则破坏了单例模式
     */
    public static void testInnerSingleton() {
        Class<?> clazz = LazyStaticInnerSingleton.class;

        try {
            Constructor c = clazz.getDeclaredConstructors()[0];
            System.out.println(c);

            // 强制暴力访问私有方法
            c.setAccessible(true);

            System.out.println(c.newInstance(null));
            System.out.println(c.newInstance(null));
            System.out.println(c.newInstance(null));
            System.out.println(c.newInstance(null));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
