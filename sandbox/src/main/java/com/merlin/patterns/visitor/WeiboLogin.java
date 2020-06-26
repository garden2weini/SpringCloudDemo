package com.merlin.patterns.visitor;

public class WeiboLogin implements  Login {
    @Override
    public void accept(Visitor visitor) {
        System.out.println(visitor.getClass().getSimpleName() +   " 通过Weibo登陆");
    }
}
