# Sandbox

## 实验清单
- 动态代理实现RPC(come.merlin.rpc)
- ZK实现分布式锁（come.merlin.sandbox.zk.ZkDemo）
- 设计模式（com.merlin.patterns）
    - Chain of Responsibility
    - Mediator
    - Proxy
    - Visitor


## 参考
- [手写Spring](https://gper.club/articles/7e7e7f7ff0g52gce)
- [Jetty Maven Plugin](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html)
- Typora (Markdown Tool)
- [Java 并发性和多线程](http://wiki.jikexueyuan.com/project/java-concurrent/)
- Slipped conditions，就是说， 从一个线程检查某一特定条件到该线程操作此条件期间，这个条件已经被其它线程改变，导致第一个线程在该条件上执行了错误的操作。这里

## 其他
### Java Volatile
- Java 中的 volatile 变量是直接从主存中读取值的变量。当一个新的值赋给一个 volatile 变量时，这个值总是会被立即写回到主存中去。这样就确保了，一个 volatile 变量最新的值总是对跑在其他 CPU 上的线程可见。其他线程每次会从主存中读取变量的值，而不是比如线程所运行 CPU 的 CPU 缓存中。
- 在一些场景下，你仅有一个线程在向一个共享变量写，多个线程在读这个变量。当仅有一个线程在更新一个变量，不管有多少个线程在读这个变量，都不会发生竞态条件。因此，无论时候当仅有一个线程在写一个共享变量时，你可以把这个变量声明为 volatile。
- 当多个线程在一个共享变量上执行一个 read-update-write 的顺序操作时才会发生竞态条件。如果你只有一个线程在执行一个 raed-update-write 的顺序操作，其他线程都在执行读操作，将不会发生竞态条件。
### 乐观锁
- java.util.concurrent.atomic.Atomic*多是乐观锁的样例
- 乐观锁允许所有的线程在不发生阻塞的情况下创建一份共享内存的拷贝。这些线程接下来可能会对它们的拷贝进行修改，并企图把它们修改后的版本写回到共享内存中。如果没有其它线程对共享内存做任何修改， CAS 操作就允许线程将它的变化写回到共享内存中去。如果，另一个线程已经修改了共享内存，这个线程将不得不再次获得一个新的拷贝，在新的拷贝上做出修改，并尝试再次把它们写回到共享内存中去。
- 称之为“乐观锁”的原因就是，线程获得它们想修改的数据的拷贝并做出修改，在乐观的假在此期间没有线程对共享内存做出修改的情况下。当这个乐观假设成立时，这个线程仅仅在无锁的情况下完成共享内存的更新。当这个假设不成立时，线程所做的工作就会被丢弃，但任然不使用锁。
### 阿姆达尔定律
阿姆达尔定律因 Gene Amdal 在 1967 年提出这个定律而得名。
阿姆达尔定律定义
一个程序（或者一个算法）可以按照是否可以被并行化分为下面两个部分：
- 可以被并行化的部分
- 不可以被并行化的部分
假设一个程序处理磁盘上的文件。这个程序的一小部分用来扫描路径和在内存中创建文件目录。做完这些后，每个文件交个一个单独的线程去处理。扫描路径和创建文件目录的部分不可以被并行化，不过处理文件的过程可以。

程序串行（非并行）执行的总时间我们记为 T。时间 T 包括不可以被并行和可以被并行部分的时间。不可以被并行的部分我们记为 B。那么可以被并行的部分就是 T-B。下面的列表总结了这些定义：
- T = 串行执行的总时间
- B = 不可以并行的总时间
- T- B = 并行部分的总时间

从上面可以得出：T = B + (T – B)

首先，这个看起来可能有一点奇怪，程序的可并行部分在上面这个公式中并没有自己的标识。然而，由于这个公式中可并行可以用总时间 T 和 B（不可并行部分）表示出来，这个公式实际上已经从概念上得到了简化，也即是指以这种方式减少了变量的个数。
T- B 是可并行化的部分，以并行的方式执行可以提高程序的运行速度。可以提速多少取决于有多少线程或者多少个 CPU 来执行。线程或者 CPU 的个数我们记为 N。可并行化部分被执行的最快时间可以通过下面的公式计算出来：
(T – B ) / N 或者通过这种方式 (1 / N) * (T – B)，维基中使用的是第二种方式。

根据阿姆达尔定律，当一个程序的可并行部分使用 N 个线程或 CPU 执行时，执行的总时间为：T(N) = B + ( T – B ) / N