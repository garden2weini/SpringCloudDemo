package com.merlin.sandbox.lock.semaphore;

/**
 * Take 方法发出一个被存放在 Semaphore 内部的信号，
 * 而 Release 方法则等待一个信号，当其接收到信号后，标记位 signal 被清空，然后该方法终止。
 */
public class RawSemaphore implements Semaphore {
    private boolean signal = false;

    public synchronized void take() {
        this.signal = true;
        this.notify();
    }

    public synchronized void release() throws InterruptedException{
        while(!this.signal) wait();
        this.signal = false;
    }
}
