package com.merlin.patterns.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class VehicalInvacationHandler implements InvocationHandler {

    //private final IVehical vehical;
    private IVehical vehical;
    private IActor actor;
    private Object impl;

    public VehicalInvacationHandler(IVehical vehical){
        this.vehical = vehical;
        this.impl = vehical;
    }

    public VehicalInvacationHandler(IActor actor){
        this.actor = actor;
        this.impl = actor;
    }

    public VehicalInvacationHandler(Car car){
        this.impl = car;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---------before-------");
        Object invoke = method.invoke(this.impl, args);
        System.out.println("---------after-------");

        return invoke;
    }
}