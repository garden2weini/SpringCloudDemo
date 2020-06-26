package com.merlin.patterns.singleton;

/**
 * 枚举式单例(属于饿汉)
 * 属于"注册式"
 */
public class Singleton4Enum {
    //私有化构造函数
    private Singleton4Enum() {
    }

    //定义一个静态枚举类
    static enum SingletonEnum {
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private Singleton4Enum user;

        //私有化枚举的构造函数
        private SingletonEnum() {
            user = new Singleton4Enum();
        }

        public Singleton4Enum getInstnce() {
            return user;
        }
    }

    //对外暴露一个获取User对象的静态方法
    public static Singleton4Enum getInstance() {
        return SingletonEnum.INSTANCE.getInstnce();
    }
}

