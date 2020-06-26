package com.merlin.patterns.aop.jdk;

public class HelloAspectImplA implements HelloAspect {

    @Override
    public String test(String sr) {
        System.out.println("Run TestAspectImplA.test " + sr);
        return sr;
    }
}
