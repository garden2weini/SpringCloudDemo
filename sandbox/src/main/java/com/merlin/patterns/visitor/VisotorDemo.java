package com.merlin.patterns.visitor;

public class VisotorDemo {
    public static void main(String[] args) {
        Visitor visitor = new YukuVisitor();
        WeiboLogin login = new WeiboLogin();
        // yuku访问者通过微博登陆
        visitor.visit(login);
    }
}
