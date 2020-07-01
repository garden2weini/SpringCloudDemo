package com.merlin.patterns.proxy.dynamic;

import java.lang.reflect.Proxy;

public class App {
    public static void main(String[] args) {
        IVehical car = new Car();

        Class[] clazzes = Car.class.getInterfaces();
        System.out.println(clazzes);


        /**
         * newProxyInstance，方法有三个参数：
         * loader: 用哪个类加载器去加载代理对象(Merlin: 代理对象返回Object时要Cast成哪种类型)
         * interfaces:动态代理类需要实现的接口(Merlin: 提供实现类都实现了哪些接口)
         * h:动态代理方法在执行时，会调用h里面的invoke方法去执行
         */
        IVehical vehical = (IVehical) Proxy.newProxyInstance(
                car.getClass().getClassLoader(),
                Car.class.getInterfaces(),
                new VehicalInvacationHandler(car));
        vehical.run();

        vehical = (IVehical) Proxy.newProxyInstance(
                IVehical.class.getClassLoader(),
                Car.class.getInterfaces(),
                new VehicalInvacationHandler(car));
        vehical.run();

        vehical = (IVehical) Proxy.newProxyInstance(
                IVehical.class.getClassLoader(),
                new Class[]{IVehical.class},
                new VehicalInvacationHandler(car));
        vehical.run();

        IActor car1 = new Car();
        IActor actor = (IActor) Proxy.newProxyInstance(
                car1.getClass().getClassLoader(),
                new Class[]{IActor.class},
                new VehicalInvacationHandler(car1));
        String result = actor.getActor("KO!");
        //System.out.println(result);

        Car car2 = new Car();
        actor = (IActor) Proxy.newProxyInstance(
                IActor.class.getClassLoader(),
                car2.getClass().getInterfaces(),
                new VehicalInvacationHandler(car2));
        actor.getActor("KO!");

    }
}
