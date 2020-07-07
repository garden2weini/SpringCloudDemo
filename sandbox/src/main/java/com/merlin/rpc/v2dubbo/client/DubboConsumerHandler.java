package com.merlin.rpc.v2dubbo.client;

import com.merlin.rpc.v2dubbo.api.DubboRequest;
import com.merlin.rpc.v2dubbo.zk.ZkWatchDog;
import com.merlin.rpc.v2dubbo.util.RandomLoadBalance;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DubboConsumerHandler implements InvocationHandler {

    private Object res;

    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ConsumerHandler(proxy, method, args));
                }
            });
            //从注册中心获取服务端ip和端口
            ZkWatchDog watchDog = new ZkWatchDog(method.getDeclaringClass().getSimpleName());
            RandomLoadBalance loadBalance = new RandomLoadBalance();

            String address = loadBalance.chooseServiceHost();
            System.out.println("Class:" + method.getDeclaringClass().getSimpleName() +
                    "\nMethod:" + method.getName() +
                    "\nHost:" + address);
            String addresses[] = address.split(":");
            ChannelFuture f = bootstrap.connect(addresses[0], Integer.parseInt(addresses[1])).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        return res;
    }

    /**
     *
     * netty-dubbo消费者拦截器
     *
     */
    private class ConsumerHandler extends ChannelInboundHandlerAdapter{
        private Object proxy;
        private Method method;
        private Object[] args;

        public ConsumerHandler(Object proxy, Method method, Object[] args) {
            this.proxy = proxy;
            this.args = args;
            this.method = method;
        }

        public void channelActive(ChannelHandlerContext ctx) {
            // 传输的对象必须实现序列化接口 包括其中的属性
            DubboRequest req = new DubboRequest(proxy.getClass().getInterfaces()[0], method.getName(), method.getParameterTypes(), args);
            ctx.write(req);
            ctx.flush();
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("调用成功");
            res = msg;
            ctx.flush();
            //收到响应后断开连接
            ctx.close();
        }

        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }
    }

}
