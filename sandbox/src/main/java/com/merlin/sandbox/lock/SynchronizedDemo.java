package com.merlin.sandbox.lock;

/**
 *
 * 1. synchronized
 * 2. Lock
 * 3. Atomic
 * 分布式锁：
 * 1. redis setNx(), 存在返回1，不存在返回0. 以此确定谁获得此锁（获得key者获得锁）
 * 2.
 */
public class SynchronizedDemo {
    static Object object = new Object();

    // 同步锁（作用范围：对象锁）
    public synchronized void demo1(){
        // 等价于 synchronized(this) {}
    }

    // 同步锁（作用范围：类锁-全局锁）
    public void demo2() {
        synchronized (object) {

        }
    }
}
