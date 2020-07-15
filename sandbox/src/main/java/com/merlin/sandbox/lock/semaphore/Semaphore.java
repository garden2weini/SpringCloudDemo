package com.merlin.sandbox.lock.semaphore;

public interface Semaphore {
    public void take() throws InterruptedException;

    public void release() throws InterruptedException;
}
