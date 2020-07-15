package com.merlin.rpc.api.impl;

import com.merlin.rpc.api.HelloServer;

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
