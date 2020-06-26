package com.merlin.patterns.visitor;

/**
 * yuku visitor
 */
public class YukuVisitor implements  Visitor {
    @Override
    public void visit(WeChatLogin login) {
        System.out.println("yuku visit");
        login.accept(this);
    }

    @Override
    public void visit(WeiboLogin login) {

    }


}
