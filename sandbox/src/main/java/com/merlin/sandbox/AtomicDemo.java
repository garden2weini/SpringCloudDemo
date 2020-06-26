package com.merlin.sandbox;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS：AbstractQueuedSynchronizer
 * 线程安全：
 * 1、可见性:
 *  JMM（Java内存模型）
 *  volatile／synchronized／final
 * 2、有序性
 *  指令重排序JVM
 *  volatile／synchronized
 * 3、原子性
 *  多个操作要么都执行成功，要么都失败
 *  synchronized／lock／AtomicInteger
 *
 */
public class AtomicDemo {
    //private static volatile int count = 0; 不能解决原子性问题
    private static int count = 0;
    private static Lock lock = new ReentrantLock(); // 重入锁 防止锁嵌套时死锁

    private static void incr() {
        try {
            lock.lock(); //加锁 将并行变串行
            Thread.sleep(10);
            count++;
            decr();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void decr() {
        try {
            lock.lock(); //加锁 将并行变串行
            Thread.sleep(10);
            count--;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AtomicDemo.incr();
                }
            }).start();
        }

        Thread.sleep(10000); // 保证等到所有线程都运行完
        System.out.println("Counter:" + count);
    }
}
