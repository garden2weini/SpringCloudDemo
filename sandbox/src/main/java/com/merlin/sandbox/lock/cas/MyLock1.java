package com.merlin.sandbox.lock.cas;

// 为了在一个多线程程序中良好的工作，”check then act” 操作必须是原子的。
// 原子就是说”check“操作和”act“被当做一个原子代码块执行。不存在多个线程同时执行原子块。
public class MyLock1 {
    private boolean locked = false;

    public synchronized boolean lock() {
        if(!locked) {
            locked = true;
            return true;
        }
        return false;
    }
}
