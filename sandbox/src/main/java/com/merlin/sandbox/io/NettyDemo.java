package com.merlin.sandbox.io;


/**
 * NOTE：NIO框架（NIO封装），不用再使用jdk api自己写NIO逻辑，并变成一个异步非阻塞IO
 * 1。写一个自己的handleer extends Netty指定的Handler
 * 2。把这个handler驾到pipeline().addLast(Handler)
 * REF：SpringMVC Controller（类似Netty Handler）的做法
 */
public class NettyDemo {
}
