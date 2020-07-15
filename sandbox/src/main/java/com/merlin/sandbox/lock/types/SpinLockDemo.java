package com.merlin.sandbox.lock.types;

public class SpinLockDemo {
    private SpinLock lock = new SpinLock();
    private int count = 0;

    public int inc() throws InterruptedException {
        lock.lock();
        int newCount = ++count;
        lock.unlock();
        return newCount;
    }

    /**
     * 注意: 其中的 while(isLocked)循环，它又被叫做“自旋锁”。
     */
    public class SpinLock {
        private boolean isLocked = false;

        public synchronized void lock()
                throws InterruptedException{
            while(isLocked){
                wait();
            }
            isLocked = true;
        }

        public synchronized void unlock(){
            isLocked = false;
            notify();
        }
    }
}
