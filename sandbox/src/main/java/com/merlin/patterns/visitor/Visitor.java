package com.merlin.patterns.visitor;

/**
 * Visitor interface
 */
public interface Visitor {
    /**
     * 对访问者而言，登陆是访问的对象
     * @param login
     */
    //void visitAiQi(Login login);
    //void visitYuKu(Login login);

    void visit(WeChatLogin login);
    void visit(WeiboLogin login);
}
