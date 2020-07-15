package com.merlin.sandbox.lock.others;

import java.util.LinkedList;
import java.util.List;

/**
 * 阻塞队列与普通队列的区别在于，当队列是空的时，从队列中获取元素的操作将会被阻塞，或者当队列是满时，往队列里添加元素的操作会被阻塞。
 * 试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其他的线程往空的队列插入新的元素。
 * 同样，试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞，直到其他的线程使队列重新变得空闲起来，如从队列中移除一个或者多个元素，或者完全清空队列.
 *
 * 阻塞队列的实现类似于带上限的 Semaphore 的实现。
 *
 * NOTE: 在 enqueue 和 dequeue 方法内部，只有队列的大小等于上限（limit）或者下限（0）时，才调用 notifyAll 方法。
 * 如果队列的大小既不等于上限，也不等于下限，任何线程调用 enqueue 或者 dequeue 方法时，都不会阻塞，都能够正常的往队列中添加或者移除元素。
 */
public class BlockingQueue {
    private List queue = new LinkedList();

    private int limit = 10;

    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void enqueue(Object item) throws InterruptedException {

        while (this.queue.size() == this.limit) {
            wait();
        }

        if (this.queue.size() == 0) {
            notifyAll();
        }

        this.queue.add(item);

    }

    public synchronized Object dequeue() throws InterruptedException {

        while (this.queue.size() == 0) {
            wait();
        }

        if (this.queue.size() == this.limit) {
            notifyAll();
        }

        return this.queue.remove(0);

    }
}
