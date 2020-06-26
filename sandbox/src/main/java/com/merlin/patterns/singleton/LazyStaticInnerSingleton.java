package com.merlin.patterns.singleton;

/**
 * 优点：线程安全、效率高
 * 缺点：能够被反射破坏、构造方法可读性差
 */
public class LazyStaticInnerSingleton {
    private LazyStaticInnerSingleton() {
        // 可以防止通过反射非法创建
        if(LazyHolder.INSTANCE != null) {
            throw new RuntimeException("（反射方法）非法通过构造函数创建单例");
        }
    }

    public static LazyStaticInnerSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    private final static class LazyHolder {
        private static final LazyStaticInnerSingleton INSTANCE = new LazyStaticInnerSingleton();
    }
}
