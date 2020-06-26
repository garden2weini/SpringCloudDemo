package com.merlin.patterns.visitor;

public class WeChatLogin implements  Login {
    @Override
    public void accept(Visitor visitor) {
        System.out.println(visitor.getClass().getSimpleName() +   " 通过WeChat登陆");
    }
}
