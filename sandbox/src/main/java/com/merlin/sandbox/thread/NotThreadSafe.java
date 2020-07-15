package com.merlin.sandbox.thread;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class NotThreadSafe {
    StringBuilder builder = new StringBuilder();

    public void add(String text){
        this.builder.append(text);
    }

    public static void main(String[] args) {
        new NotThreadSafe().test();
    }

    /**
     * 两个 MyRunnable 共享了同一个 NotThreadSafe 对象。因此，当它们调用 add()方法时会造成竞态条件(race conditions)。
     */
    public void test() {
        CountDownLatch latch = new CountDownLatch(1);
        NotThreadSafe sharedInstance = new NotThreadSafe();
        int N = 200;
        for(int i=0; i< N; i++) {
            new Thread(new MyRunnable(sharedInstance, latch)).start();
        }
        // 保证N个线程同时执行！
        latch.countDown();
        // NOTE: 由于竞态条件和临界区的原因，期望中的N个1基本上是出不来的
        System.out.println(sharedInstance.builder.toString());

    }

    public class MyRunnable implements Runnable{
        NotThreadSafe instance = null;
        CountDownLatch latch = null;

        public MyRunnable(NotThreadSafe instance, CountDownLatch latch){
            this.latch = latch;
            this.instance = instance;
        }

        public void run(){
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.instance.add("1");
        }
    }
}
