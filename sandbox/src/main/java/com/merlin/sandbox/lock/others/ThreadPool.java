package com.merlin.sandbox.lock.others;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 线程池的实现由两部分组成:
 * 类 ThreadPool 是线程池的公开接口，
 * 类 PoolThread 用来实现执行任务的子线程。
 */
public class ThreadPool {
    private BlockingQueue<Runnable> taskQueue = null;
    private List<PoolThread> threads = new ArrayList<PoolThread>();
    private boolean isStopped = false;

    /**
     *
     * @param noOfThreads 线程初始数量
     * @param maxNoOfTasks 最大任务数
     */
    public ThreadPool(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new ArrayBlockingQueue(maxNoOfTasks);

        for (int i=0; i< noOfThreads; i++) {
            threads.add(new PoolThread(taskQueue));
        }
        for (PoolThread thread : threads) {
            thread.start();
        }
    }

    /**
     * 在内部，Runnable 对象被放入阻塞队列 (Blocking Queue) ，等待着被子线程取出队列。
     * @param task
     * @throws InterruptedException
     */
    public synchronized void execute(Runnable task) throws InterruptedException {
        if(this.isStopped) throw
                new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.add(task);
    }

    /**
     * 调用 ThreadPool.stop()方法可以停止 ThreadPool。在内部，调用 stop 先会标记 isStopped 成员变量（为 true）。
     * 然后，线程池的每一个子线程都调用 PoolThread.stop()方法停止运行。
     * 注意，如果线程池的 execute()在 stop()之后调用，execute()方法会抛出 IllegalStateException 异常。
     * @return
     */
    public synchronized boolean stop() {
        this.isStopped = true;
        for (PoolThread thread : threads) {
            //thread.stop();
            thread.toStop();
        }
        return this.isStopped;
    }

    public class PoolThread extends Thread {

        private BlockingQueue<Runnable> taskQueue = null;
        private boolean isStopped = false;

        public PoolThread(BlockingQueue<Runnable> queue) {
            taskQueue = queue;
        }

        // 一个空闲的 PoolThread 线程会把 Runnable 对象从队列中取出并执行。
        // 执行完毕后，PoolThread 进入循环并且尝试从队列中再取出一个任务，直到线程终止。
        public void run() {
            while (!isStopped()) {
                try {
                    Runnable runnable = taskQueue.take();
                    runnable.run();
                } catch (Exception e) {
                    // 写日志或者报告异常,
                    // 但保持线程池运行.
                }
            }
        }

        public synchronized void toStop() {
            isStopped = true;
            // 打断池中线程的 dequeue() 调用.
            this.interrupt();
        }

        public synchronized boolean isStopped() {
            return isStopped;
        }
    }
}


