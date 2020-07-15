package com.merlin.sandbox.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * NOTE：通过ZK短暂有序节点实现分布式锁
 * REF：https://mp.weixin.qq.com/s/u8QDlrDj3Rl1YjY4TyKMCA
 */
public class ZkDemo {
    public static void main(String[] args) throws Exception {
        String path = "/dist_lock";
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost", 5000, 4000,
                new ExponentialBackoffRetry(1000, 3));
        // connect
        curatorFramework.start();
        // CRUD
        try {
            curatorFramework.create().forPath(path, "merlin".getBytes());
        } catch (Exception e) {
            //e.printStackTrace();
        }
        // 分布式互斥锁
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, path + "/locks");
        // 模拟多个请求
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "尝试获取锁");
                try {
                    lock.acquire();
                    System.out.println(Thread.currentThread().getName() + " 获取锁成功.");
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                        System.out.println(Thread.currentThread().getName() + " 释放了锁.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "T" + i).start();
        }


    }
}
