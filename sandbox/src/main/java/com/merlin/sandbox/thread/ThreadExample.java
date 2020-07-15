package com.merlin.sandbox.thread;

public class ThreadExample {
    public static void main(String[] args){
        //ThreadExample.testRunStart();
        ThreadExample.testRunStart2();
    }

    /**
     * NOTE: Jvm 和操作系统一起决定了线程的执行顺序，他和线程的启动顺序并非一定是一致的。
     * WARN: run()方法并非是由刚创建的新线程所执行的，而是被创建新线程的当前线程所执行了。也就是被执行上面两行代码的线程所执行的。想要让创建的新线程执行 run()方法，必须调用新线程的 start 方法。
     */
    public static void testRunStart() {
        System.out.println(Thread.currentThread().getName());
        for(int i=0; i<10; i++){
            new Thread("" + i){
                public void run(){
                    // run()时，Thread.currentThread().getName()得到的是"main",而不是新的线程号
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("Thread: " + getName() + " running");
                }
            }.start();
            //}.run(); // ERROR: not run, use start
        }
    }

    public static void testRunStart2() {
        System.out.println(Thread.currentThread().getName());
        Runnable myRunnable = new Runnable(){
            public void run(){
                // run()时，Thread.currentThread().getName()得到的是"main",而不是新的线程号
                System.out.println("Thread: " + Thread.currentThread().getName() + " running");
            }
        };
        for(int i=0; i<10; i++){
            new Thread(myRunnable,"" + i)
            //        .start();
            .run(); // ERROR: not run, use start
        }
    }


}
