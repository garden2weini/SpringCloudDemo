package com.merlin.patterns.aop.jdk;

public class HelloAspectImpl implements HelloAspect {

    @Override
    public String test(String sr) {
        System.out.println("Run TestAspectImpl.test " + sr);
        return sr;
    }
}
