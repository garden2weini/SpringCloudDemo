package com.merlin.sandbox.lock.cas;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 现在 CPU 内部已经执行原子的 CAS 操作。
 * Java5 以来，你可以使用 java.util.concurrent.atomic 包中的一些原子类来使用 CPU 中的这些功能。
 *
 * 使用 Java5+提供的 CAS 特性而不是使用自己实现的的好处是 Java5+中内置的 CAS 特性可以让你利用底层的你的程序所运行机器的 CPU 的 CAS 特性。
 * 这会使还有 CAS 的代码运行更快。
 *
 * AtomicBoolean和AtomicLong等都是乐观锁（即cas原子化）：
 * 乐观锁允许所有的线程在不发生阻塞的情况下创建一份共享内存的拷贝。这些线程接下来可能会对它们的拷贝进行修改，并企图把它们修改后的版本写回到共享内存中。如果没有其它线程对共享内存做任何修改， CAS 操作就允许线程将它的变化写回到共享内存中去。如果，另一个线程已经修改了共享内存，这个线程将不得不再次获得一个新的拷贝，在新的拷贝上做出修改，并尝试再次把它们写回到共享内存中去。
 * 称之为“乐观锁”的原因就是，线程获得它们想修改的数据的拷贝并做出修改，在乐观的假在此期间没有线程对共享内存做出修改的情况下。当这个乐观假设成立时，这个线程仅仅在无锁的情况下完成共享内存的更新。当这个假设不成立时，线程所做的工作就会被丢弃，但任然不使用锁。
 */
public class MyLock2 {
    private AtomicBoolean locked = new AtomicBoolean(false);

    public boolean lock() {
        return locked.compareAndSet(false, true);
    }
}
