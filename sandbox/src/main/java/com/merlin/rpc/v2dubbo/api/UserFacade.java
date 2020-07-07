package com.merlin.rpc.v2dubbo.api;

public class UserFacade implements IUserFacade {

    public String getUserName(Long id) {

        return "I love you, "+id;
    }

}
