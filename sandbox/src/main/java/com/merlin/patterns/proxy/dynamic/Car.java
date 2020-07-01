package com.merlin.patterns.proxy.dynamic;

public class Car implements IVehical, IActor {
    @Override
    public void run() {
        System.out.println("Car会跑");
    }

    @Override
    public String getActor(String arg) {
        String result = "Actor is Merlin." + arg;
        System.out.println(result);
        return result;
    }
}
