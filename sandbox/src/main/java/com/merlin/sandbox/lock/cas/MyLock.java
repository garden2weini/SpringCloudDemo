package com.merlin.sandbox.lock.cas;

/**
 * 经常出现的模式就是“check and act”模式。先检查后操作模式发生在代码中首先检查一个变量的值，然后再基于这个值做一些操作。
 *
 * 如果同个线程访问同一个 MyLock 实例，上面的 lock()将不能保证正常工作。
 *  如果一个线程检查 locked 的值，然后将其设置为 false，与此同时，一个线程 B 也在检查 locked 的值，又或者，在线程 A 将 locked 的值设为 false 之前。
 *  因此，线程 A 和线程 B 可能都看到 locked 的值为 false，然后两者都基于这个信息做一些操作。
 */
public class MyLock {
    private boolean locked = false;

    public boolean lock() {
        if(!locked) {
            locked = true;
            return true;
        }
        return false;
    }
}
