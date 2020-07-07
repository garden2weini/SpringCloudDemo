package com.merlin.rpc.v2dubbo.client;

import java.lang.reflect.Proxy;

public class DubboProxy {

    public static Object getProxyInstance(Class<?> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new DubboConsumerHandler());
    }
}
