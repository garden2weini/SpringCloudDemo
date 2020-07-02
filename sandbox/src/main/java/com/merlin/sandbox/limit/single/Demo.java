package com.merlin.sandbox.limit.single;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 单一接口限流：服务的并发限流
 * 效果是有些请求能处理，有些不能处理！
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        HelloServcie helloServcie = new HelloServcie();
        // 计数器
        CountDownLatch latch = new CountDownLatch(1);
        Random random = new Random(10);
        for(int i=0; i<20; i++) {
            Thread t = new Thread(()->{
               try {
                   // 线程创建后不运行，先等待
                   latch.await();
                   Thread.sleep(random.nextInt(1000));
                   helloServcie.doRequest();
               } catch(InterruptedException e) {
                   e.printStackTrace();
               }
            });
            t.start();
        }
        // 20个线程创建完后，统一执行并访问服务
        latch.countDown();
        System.in.read();
    }
}
