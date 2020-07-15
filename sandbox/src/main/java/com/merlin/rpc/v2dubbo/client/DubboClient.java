package com.merlin.rpc.v2dubbo.client;

import com.merlin.rpc.api.HelloServer;
import com.merlin.rpc.api.UserFacade;

public class DubboClient {

    public static void main(String[] args){
        UserFacade userFacade = (UserFacade) DubboProxy.getProxyInstance(UserFacade.class);

        System.out.println("..."+ userFacade.getUserName(520L));
        System.out.println("..."+ userFacade.getUserName(1314L));
        System.out.println("..."+ userFacade.getUserName(1314520L));

        HelloServer helloServer = (HelloServer) DubboProxy.getProxyInstance(HelloServer.class);
        String s = helloServer.sayHello("say hello method...over netty by zk..");
        System.out.println("..."+ s);
        s = helloServer.sayHello2("say hello2 method...over netty by zk..");
        System.out.println("..."+ s);
    }
}

