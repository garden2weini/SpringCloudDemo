package com.merlin.sandbox.lock.types;

/**
 * Java 中的 synchronized 同步块是可重入的。这意味着如果一个 java 线程进入了代码中的 synchronized 同步块，并因此获得了该同步块使用的同步对象对应的管程上的锁，那么这个线程可以进入由同一个管程对象所同步的另一个 java 代码块。
 * 注意 outer()和 inner()都被声明为 synchronized，这在 Java 中和 synchronized(this)块等效。如果一个线程调用了 outer()，在 outer()里调用 inner()就没有什么问题，因为这两个方法（代码块）都由同一个管程对象（”this”)所同步。如果一个线程已经拥有了一个管程对象上的锁，那么它就有权访问被这个管程对象同步的所有代码块。
 * NOTE: 可重入:线程可以进入任何一个它已经拥有的锁所同步着的代码块。
 */
public class Reentrant1Demo {
    public synchronized void outer(){
        inner();
    }

    public synchronized void inner(){
        //do something
    }
}
