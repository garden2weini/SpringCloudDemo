package com.merlin.sandbox.jvmoom;

import java.util.ArrayList;

/**
 * OOM
 * jvisualvm to view
 */
public class HeapTest {
    byte[] a = new byte[1024 * 100];

    public static void main(String[] args) {
        ArrayList<HeapTest> list  = new ArrayList<>();

        while(true) {
            list.add(new HeapTest());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
