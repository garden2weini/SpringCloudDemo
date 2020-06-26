package com.merlin.sandbox;

/**
 * NOTE: 线程切换，导致ThreadLocal的数据丢失，可以改用InheritableThreadLocal来解决！
 * REF: Hystrix隔离模式详解与Zuul做灰度发布／Spring Cloud中使用Hystrix 线程隔离导致ThreadLocal数据丢失(http://www.60kb.com/post/94.html)
 */
public class CustomThreadLocal {
    static ThreadLocal threadLocal = new ThreadLocal<>();
    // InheritableThreadLocal是为了解决线程切换导致ThreadLocal拿不到值的问题而产生的。
    //static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CustomThreadLocal.threadLocal.set("一花一世界");
                new Service().call();
            }
        }).start();
    }
}

class Service {
    public void call() {
        System.out.println("Service:" + Thread.currentThread().getName());
        System.out.println("Service:" + CustomThreadLocal.threadLocal.get());
        new Dao().call();
        /*
        new Thread(new Runnable() {

            @Override
            public void run() {
                new Dao().call();
            }
        }).start();

         */
    }
}

class Dao {
    public void call() {
        System.out.println("==========================");
        System.out.println("Dao:" + Thread.currentThread().getName());
        System.out.println("Dao:" + CustomThreadLocal.threadLocal.get());
    }
}
