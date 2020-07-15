package com.merlin.rpc.v1.client;

import com.merlin.rpc.v1.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {
    private int port;
    private String host;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * invoke三个参数：
     * @param proxy 就是代理对象，newProxyInstance方法的返回对象
     * @param method 调用的方法
     * @param args 方法中的参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);

        RpcNetTransport rpcNetTransport = new RpcNetTransport(this.host, this.port);
        return rpcNetTransport.send(rpcRequest);
    }
}
