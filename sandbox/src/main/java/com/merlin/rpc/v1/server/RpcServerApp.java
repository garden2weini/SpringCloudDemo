package com.merlin.rpc.v1.server;

import com.merlin.rpc.Const;
import com.merlin.rpc.api.impl.HelloServerImpl;

public class RpcServerApp {
    public static void main(String[] args) {
        HelloServerImpl impl = new HelloServerImpl();
        RpcProxyServer proxyServer = new RpcProxyServer();
        proxyServer.publish(Const.PORT, impl);
    }
}
