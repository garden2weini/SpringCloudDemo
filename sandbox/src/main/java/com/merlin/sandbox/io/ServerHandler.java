package com.merlin.sandbox.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO；阻塞IO
 * NIO: New IO/NO-Blocking IO 同步非阻塞IO（Selector Channel Buffer）
 */
public class ServerHandler implements Runnable {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }


    public static void main(String[] args) {

    }
    /**
     * 2。多线程
     */
    public void server2() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8000);
            System.out.println("Server Start successful, listening port 8000, wating for client connecting...");
            while(true) {
                Socket socket = server.accept();
                // 针对每个连接创建一个线程，去处理IO操作
                // NOTE 客户端连进来就要建立一个线程，但此时socket并没有io操作，所以浪费此线程
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3。线程池
     */
    public void server3() {
        ServerSocket server = null;
        ExecutorService executorService = Executors.newFixedThreadPool(60);
        try {
            server = new ServerSocket(8000);
            System.out.println("Server Start successful, listening port 8000, wating for client connecting...");
            while(true) {
                Socket socket = server.accept();
                executorService.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
