package com.merlin.rpc.v2dubbo.server;

import com.merlin.rpc.Const;
import com.merlin.rpc.v2dubbo.zk.ServiceRegister;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetAddress;

/**
 *
 */
public class DubboServer {

    private int port;

    public DubboServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(1024*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new DubboServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        //获得IP
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        // 注册服务
        ServiceRegister.register("/IUserFacade", hostAddress, Const.PORT);

        new DubboServer(Const.PORT).run();


    }

}
