package com.merlin.sandbox.lock.types;

import java.util.HashMap;
import java.util.Map;

/**
 * 可重入的 ReadWriteLock 的完整实现
 *
 * 读/写锁使用场景：
 * 假设程序中涉及到对一些共享资源的读和写操作，且写操作没有读操作那么频繁。
 * 在没有写操作的时候，两个线程同时读一个资源没有任何问题，所以应该允许多个线程能在同时读取共享资源。
 * 但是如果有一个线程想去写这些共享资源，就不应该再有其它线程对该资源进行读或写
 * 也就是说：读-读能共存，读-写不能共存，写-写不能共存。
 *
 * 在 finally 中调用 unlock()
 * lock.lockWrite();
 * try{
 *     //do critical section code, which may throw exception
 * } finally {
 *     lock.unlockWrite();
 * }
 **/
public class ReadWriteLock {
    private Map<Thread, Integer> readingThreads =
            new HashMap<Thread, Integer>();

    private int writeAccesses    = 0;
    private int writeRequests    = 0;
    private Thread writingThread = null;

    /**
     * 防止写线程"饥饿"现象：
     * 只有当没有线程正在锁住 ReadWriteLock 进行写操作，且没有线程请求该锁准备执行写操作时，才能保证读操作继续。
     * 读锁重入：
     * 要保证某个线程中的读锁可重入，要么满足获取读锁的条件（没有写或写请求），要么已经持有读锁（不管是否有写请求）。
     * 要确定一个线程是否已经持有读锁，可以用一个 map 来存储已经持有读锁的线程以及对应线程获取读锁的次数，当需要判断某个线程能否获得读锁时，就利用 map 中存储的数据进行判断。
     * @throws InterruptedException
     */
    public synchronized void lockRead()
            throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(! canGrantReadAccess(callingThread)){
            wait();
        }

        readingThreads.put(callingThread,
                (getReadAccessCount(callingThread) + 1));
    }

    private boolean canGrantReadAccess(Thread callingThread){
        if(isWriter(callingThread)) return true;
        if(hasWriter()) return false;
        if(isReader(callingThread)) return true;
        if(hasWriteRequests()) return false;
        return true;
    }

    public synchronized void unlockRead(){
        Thread callingThread = Thread.currentThread();
        if(!isReader(callingThread)){
            throw new IllegalMonitorStateException(
                    "Calling Thread does not" +
                            " hold a read lock on this ReadWriteLock");
        }
        int accessCount = getReadAccessCount(callingThread);
        if(accessCount == 1){
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, (accessCount -1));
        }
        notifyAll();
    }

    /**
     * 写锁重入:
     * 仅当一个线程已经持有写锁，才允许写锁重入（再次获得写锁）。
     * 读锁升级到写锁:
     * 希望一个拥有读锁的线程，也能获得写锁。想要允许这样的操作，要求这个线程是唯一一个拥有读锁的线程。
     * @throws InterruptedException
     */
    public synchronized void lockWrite()
            throws InterruptedException{
        writeRequests++;
        Thread callingThread = Thread.currentThread();
        while(!canGrantWriteAccess(callingThread)){
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    public synchronized void unlockWrite()
            throws InterruptedException{
        if(!isWriter(Thread.currentThread())) {
            throw new IllegalMonitorStateException(
                    "Calling Thread does not" +
                            " hold the write lock on this ReadWriteLock");
        }
        writeAccesses--;
        if(writeAccesses == 0){
            writingThread = null;
        }
        notifyAll();
    }

    /**
     * 写锁降级到读锁:
     * 有时拥有写锁的线程也希望得到读锁。如果一个线程拥有了写锁，那么自然其它线程是不可能拥有读锁或写锁了。
     * 所以对于一个拥有写锁的线程，再获得读锁，是不会有什么危险的。
     * @param callingThread
     * @return
     */
    private boolean canGrantWriteAccess(Thread callingThread){
        if(isOnlyReader(callingThread)) return true;
        if(hasReaders()) return false;
        if(writingThread == null) return true;
        if(!isWriter(callingThread)) return false;
        return true;
    }

    private int getReadAccessCount(Thread callingThread){
        Integer accessCount = readingThreads.get(callingThread);
        if(accessCount == null) return 0;
        return accessCount.intValue();
    }

    private boolean hasReaders(){
        return readingThreads.size() > 0;
    }

    private boolean isReader(Thread callingThread){
        return readingThreads.get(callingThread) != null;
    }

    private boolean isOnlyReader(Thread callingThread){
        return readingThreads.size() == 1 &&
                readingThreads.get(callingThread) != null;
    }

    private boolean hasWriter(){
        return writingThread != null;
    }

    private boolean isWriter(Thread callingThread){
        return writingThread == callingThread;
    }

    private boolean hasWriteRequests(){
        return this.writeRequests > 0;
    }
}
