package com.merlin.sandbox.lock.semaphore;

/**
 * 当信号量的数量上限是 1 时，Semaphore 可以被当做锁来使用。通过 take 和 release 方法来保护关键区域。
 * 通过有上限的 Semaphore 可以限制进入某代码块的线程数量。
 * 设想一下，如果 BoundedSemaphore 上限设为 5 将会发生什么？意味着允许 5 个线程同时访问关键区域，
 * 但是必须保证，这5个线程不会互相冲突。否则你的应用程序将不能正常运行。
 */
public class BoundedSemaphoreDemo {
    public static void main(String[] args) throws InterruptedException {
        //BoundedSemaphoreDemo.test1();
        BoundedSemaphoreDemo.test2();
    }

    /**
     * TODO：线程池的获取与释放应该可以通过有上限信号量来控制
     */
    public static void test2() {
        Semaphore semaphore = new BoundedSemaphore(5);

        SendingThread sender = new SendingThread(semaphore);
        ReceivingThread receiver = new ReceivingThread(semaphore);

        receiver.start();
        sender.start();
    }

    public static void test1() throws InterruptedException {
        BoundedSemaphore semaphore = new BoundedSemaphore(1);

        semaphore.take();
        try{
            //critical section
        } finally {
            semaphore.release();

        }
    }

}
