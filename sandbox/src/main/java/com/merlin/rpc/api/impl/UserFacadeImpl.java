package com.merlin.rpc.api.impl;

import com.merlin.rpc.api.UserFacade;

public class UserFacadeImpl implements UserFacade {

    public String getUserName(Long id) {
        return "I love you, "+id;
    }

}
