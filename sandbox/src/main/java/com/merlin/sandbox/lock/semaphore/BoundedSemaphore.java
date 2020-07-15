package com.merlin.sandbox.lock.semaphore;

/**
 * 有上限的 Semaphore
 * 当已经产生的信号数量达到了上限，take 方法将阻塞新的信号产生请求，直到某个线程调用 release 方法后，
 * 被阻塞于 take 方法的线程才能传递自己的信号。
 */
public class BoundedSemaphore implements Semaphore {
    private int signals = 0;
    private int bound   = 0;

    public BoundedSemaphore(int upperBound){
        this.bound = upperBound;
    }

    public synchronized void take() throws InterruptedException{
        while(this.signals == bound) wait();
        this.signals++;
        this.notify();

    }

    public synchronized void release() throws InterruptedException{
        while(this.signals == 0) wait();
        this.signals--;
        this.notify();
    }
}
