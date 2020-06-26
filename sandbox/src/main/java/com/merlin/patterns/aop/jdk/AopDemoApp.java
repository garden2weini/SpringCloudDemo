package com.merlin.patterns.aop.jdk;

public class AopDemoApp {
    public static HelloAspect testAspect = new HelloAspectImpl();
    public static HelloAspect testAspectA = new HelloAspectImplA();

    // NOTE: 这里是无效的!
    public static void main(String []args) {
        HelloWorldAspect.init();
        //HelloAspect testAspect = new HelloAspectImpl();
        //HelloAspect testAspectA = new HelloAspectImplA();

        testAspect.test("I'm TestAspectImpl");
        testAspectA.test("I'm TestAspectImplA");
    }
}
