package com.merlin.patterns.aop.spring;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 为特定的切面生命(before/after)行为
 */
@Aspect
@Component
public class LogPrintAspect {
    //自定义切点位置
    //把切面连接点放在我们注解上
    @Pointcut("@annotation(com.merlin.patterns.aop.spring.LogPrint)")
    private void controllerAspect(){
        System.out.println("Not run in controller Aspect method, just a aspect label method!");
    }

    //自定义前置切面
    //访问controller方法前先执行的方法
    @Before("controllerAspect()")
    public void printLog(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
        System.out.println(sdf.format(new Date())+" || no attribute value!");
    }

    @After("controllerAspect()")
    public void afterPrintLog(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
        System.out.println(sdf.format(new Date())+" || (after)no attribute value!");
    }

    //访问controller方法前先执行的方法-获取注解属性
    @Before(value = "controllerAspect()&&@annotation(logPrint)", argNames = "logPrint")
    public void printLog(LogPrint logPrint){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
        System.out.println(sdf.format(new Date())+ " || " + logPrint.desc());
    }
}
