package com.merlin.sandbox.lock.cas;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * A-B-A 问题指的是一个变量被从 A 修改到了 B，然后又被修改回 A 的一种情景。其他线程对于这种情况却一无所知。
 * 如果线程 A 检查正在进行的数据更新，拷贝，被线程调度器挂起，一个线程 B 在此期可能可以访问这个共享数据结构。如果线程对这个数据结构执行了全部的更新，移除了它的预期修改，这样看起来，好像线程 A 自从拷贝了这个数据结构以来没有对它做任何的修改。然而，一个修改确实已经发生了。当线程 A 继续基于现在已经过期的数据拷贝执行它的更新时，这个数据修改已经被线程 B 的修改破坏。
 *
 * 在 Java 中，你不能将一个引用和一个计数器归并在一起形成一个单个的变量。
 * 不过 Java 提供了 AtomicStampedReference 类，利用这个类可以使用一个 CAS 操作自动的替换一个引用和一个标记（stamp）。
 * 类似于 CAS+版本号，通过版本号来解决A-B-A问题（CAS原子化解决线程可见，版本号解决顺序更改）
 * ref: https://wiki.jikexueyuan.com/project/java-concurrent/non-blocking-algorithms.html
 */
public class NonblockingTemplate {
    public static class IntendedModification{
        public AtomicBoolean completed = new AtomicBoolean(false);
    }

    private AtomicStampedReference<IntendedModification> ongoingMod = new AtomicStampedReference<IntendedModification>(null, 0);
    //declare the state of the data structure here.

    public void modify(){
        while(!attemptModifyASR());
    }

    public boolean attemptModifyASR(){
        boolean modified = false;

        IntendedModification currentlyOngoingMod = ongoingMod.getReference();
        int stamp = ongoingMod.getStamp();

        if(currentlyOngoingMod == null){
            //copy data structure - for use
            //in intended modification

            //prepare intended modification
            IntendedModification newMod = new IntendedModification();

            boolean modSubmitted = ongoingMod.compareAndSet(null, newMod, stamp, stamp + 1);

            if(modSubmitted){
                //complete modification via a series of compare-and-swap operations.
                //note: other threads may assist in completing the compare-and-swap
                // operations, so some CAS may fail
                modified = true;
            }
        }else{
            //attempt to complete ongoing modification, so the data structure is freed up
            //to allow access from this thread.
            modified = false;
        }

        return modified;
    }
}
