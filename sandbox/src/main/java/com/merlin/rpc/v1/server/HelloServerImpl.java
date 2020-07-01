package com.merlin.rpc.v1.server;

import com.merlin.rpc.v1.HelloServer;

public class HelloServerImpl implements HelloServer {
    public String sayHello(String arg) {
       System.out.println("Service Provider1...HelloServer.sayHello2");
       return "hello:" + arg;
    }

    public String sayHello2(String arg) {
        System.out.println("Service Provider1...HelloServer.sayHello2");
        return "hello2:" + arg;
    }
}
