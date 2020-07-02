package com.merlin.sandbox.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * NOTE：通过ZK短暂有序节点实现分布式锁
 */
public class ZkDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost", 5000, 4000,
                new ExponentialBackoffRetry(1000, 3));
        // connect
        curatorFramework.start();
        // CRUD
        curatorFramework.create().forPath("/merlin/curaot666", "merlin".getBytes());
        // 分布式锁
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/locks");
        // 模拟多个请求
        for(int i =0; i<20; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "尝试获取锁");
                try {
                    lock.acquire();
                    System.out.println(Thread.currentThread().getName()+" 获取锁成功.");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                        System.out.println(Thread.currentThread().getName()+" 锁释了放.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "T"+i).start();
        }



    }
}
