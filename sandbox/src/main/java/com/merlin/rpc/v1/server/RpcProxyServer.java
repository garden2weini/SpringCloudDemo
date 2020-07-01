package com.merlin.rpc.v1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProxyServer {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private int port;
    public void RpcProxyServer(int port) {
        this.port = port;

    }

    public void publish(int port, HelloServerImpl impl) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                ProcessHandler processHandler = new ProcessHandler(impl, socket);
                executorService.execute(processHandler);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
