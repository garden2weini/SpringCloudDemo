package com.merlin.rpc.v1.client;

import com.merlin.rpc.v1.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcNetTransport {
    private int port;

    public RpcNetTransport(String host, int port) {
        this.port = port;
        this.host = host;
    }

    private String host;


    private Socket newSocket() throws IOException {
        System.out.println("Server:" + this.host);
        System.out.println("Port:" + this.port);
        Socket socket = new Socket(host, port);
        return socket;
    }
    public Object send(RpcRequest request) {
        Socket socket = null;
        ObjectOutputStream outputStream;
        ObjectInputStream inputStream;
        Object result = null;
        try {
            socket = newSocket();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}
