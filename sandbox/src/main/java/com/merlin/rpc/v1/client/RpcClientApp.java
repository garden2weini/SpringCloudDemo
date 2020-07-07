package com.merlin.rpc.v1.client;

import com.merlin.rpc.Const;
import com.merlin.rpc.v1.HelloServer;

public class RpcClientApp {
    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        HelloServer helloServer = rpcProxyClient.clientProxy(HelloServer.class, "localhost", Const.PORT);
        String obj = helloServer.sayHello("-- (RPC Client)Hello Rpc. KO!");
        System.out.println(obj);
        obj = helloServer.sayHello(" -- to sayHello2");
        System.out.println(obj);
    }
}
