package com.merlin.rpc.v2dubbo.client;

import com.merlin.rpc.v2dubbo.api.IUserFacade;

public class DubboClient {

    public static void main(String[] args){
        IUserFacade userFacade = (IUserFacade) DubboProxy.getProxyInstance(IUserFacade.class);

        System.out.println("..."+ userFacade.getUserName(520L));
        System.out.println("..."+ userFacade.getUserName(1314L));
        System.out.println("..."+ userFacade.getUserName(1314520L));
    }
}

