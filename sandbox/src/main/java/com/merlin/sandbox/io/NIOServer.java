package com.merlin.sandbox.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 三个主要组件：Selector Channel(ServerSocketChannel SocketChannel) Buffer
 */
public class NIOServer {
    private int port = 8000;
    private InetSocketAddress address = null;
    private Selector selector;

    public NIOServer(int port) {
        try {
            this.port = port;
            address = new InetSocketAddress(this.port);
            ServerSocketChannel server = ServerSocketChannel.open();
            server.socket().bind(address);;
            // 服务端通道设置非阻塞的模式
            //server.configureBlocking(false);
            selector = Selector.open();
            // 每当有客户端连接上来的时候 默认它已经连上来了
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server Start OK: " + this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NIOServer(8000).listen();
    }

    public void listen() {
        try {
            // lunxun
            while(true){
                int wait = this.selector.select(); // leisi accept() yeshizusede
                if(wait == 0) {
                    continue;
                }
                // SelectionKey代表的客户端和服务端连接的一个关键
                Set<SelectionKey> keys = this.selector.selectedKeys();
                Iterator<SelectionKey> i = keys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();
                    // 真对每一个客户端进行相应的操作
                    process(key);
                    i.remove();
                }

            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void process(SelectionKey key) {
    }
}
