package com.merlin.sandbox.lock.semaphore;

/**
 * Semaphore（信号量）是一个线程同步结构，用于在线程间传递信号，以避免出现信号丢失，或者像锁一样用于保护一个关键区域。
 *
 * 使用这个 semaphore 可以避免错失某些信号通知。
 * 用 take 方法来代替 notify，release 方法来代替 wait。
 * 如果某线程在调用 release 等待之前调用 take 方法，那么调用 release 方法的线程仍然知道 take 方法已经被某个线程调用过了，
 * 因为该 Semaphore 内部保存了 take 方法发出的信号。而 wait 和 notify 方法就没有这样的功能。
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new RawSemaphore();

        SendingThread sender = new SendingThread(semaphore);
        ReceivingThread receiver = new ReceivingThread(semaphore);

        receiver.start();
        sender.start();
    }

}

class SendingThread extends Thread {
    Semaphore semaphore = null;

    public SendingThread(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    public void run(){
        while(true){
            //do something, then signal
            System.out.println("SendingThread: do something, then signal...");
            try {
                this.semaphore.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ReceivingThread extends Thread {
    Semaphore semaphore = null;

    public ReceivingThread(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    public void run(){
        while(true){
            try {
                this.semaphore.release();
                //receive signal, then do something...
                System.out.println("ReceivingThread: receive signal, then do something...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
