package com.merlin.rpc.v1.client;

import java.lang.reflect.Proxy;

public class RpcProxyClient {
    /**
     * Java动态代理Proxy.newProxyInstance
     * 利用Java的反射技术(Java Reflection)，在运行时创建一个实现某些给定接口的新类（也称“动态代理类”）及其实例（对象）,
     * 代理的是接口(Interfaces)，不是类(Class)，也不是抽象类。在运行时才知道具体的实现.
     * @param interfaces
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public <T> T clientProxy(Class interfaces, String host, int port) {
        /**
         * newProxyInstance，方法有三个参数：
         * loader: 用哪个类加载器去加载代理对象
         * interfaces:动态代理类需要实现的接口
         * h:动态代理方法在执行时，会调用h里面的invoke方法去执行
         */
        return (T) Proxy.newProxyInstance(
                interfaces.getClassLoader(),
                new Class[]{interfaces},
                new RemoteInvocationHandler(host, port));
    }
}
