package com.merlin.patterns.visitor;

/**
 * Login interface
 */
public interface Login {
    /**
     * 对于登陆业务而言，访问者是被接受的；
     * 无论visjtor的来源是哪里
     * @param visitor
     */
    void accept(Visitor visitor);
}
