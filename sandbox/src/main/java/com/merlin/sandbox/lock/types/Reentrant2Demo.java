package com.merlin.sandbox.lock.types;

public class Reentrant2Demo {
    private ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public int inc() throws InterruptedException {
        lock.lock();
        int newCount = ++count;
        lock.unlock();
        return newCount;
    }

    /**
     * 可重入锁实现
     */
    public class ReentrantLock {
        boolean isLocked = false;
        Thread lockedBy = null;
        int lockedCount = 0;

        public synchronized void lock()
                throws InterruptedException {
            Thread callingThread =
                    Thread.currentThread();
            while (isLocked && lockedBy != callingThread) {
                wait();
            }
            isLocked = true;
            lockedCount++;
            lockedBy = callingThread;
        }

        public synchronized void unlock() {
            if (Thread.currentThread() ==
                    this.lockedBy) {
                lockedCount--;

                if (lockedCount == 0) {
                    isLocked = false;
                    notify();
                }
            }
        }
    }
}
