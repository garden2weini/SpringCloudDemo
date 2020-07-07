package com.merlin.rpc.v2dubbo.server;

import java.lang.reflect.Method;

import com.merlin.rpc.v2dubbo.api.DubboRequest;
import com.merlin.rpc.v2dubbo.api.IUserFacade;
import com.merlin.rpc.v2dubbo.api.UserFacade;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * netty-dubbo服务端拦截器
 *
 */
public class DubboServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端收到消息:   " + msg);
        DubboRequest req = (DubboRequest) msg;
        // 1. 根据类名返回对象
        Object target = this.getInstenceByInterfaceClass(req.getInterfaceClass());
        // 2. 获取方法名
        String methodName = req.getMethodName();
        // 3. 获取方法参数类型
        // 4. 获取方法
        Method method = target.getClass().getMethod(methodName, req.getParamTypes());
        // 5. 获取参数值
        //调用方法 获取返回值
        Object res = method.invoke(target, req.getArgs());
        // 写回给调用端
        ctx.writeAndFlush(res);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 根据接口返回对应的实例
     * @param clazz
     * @return
     */
    private Object getInstenceByInterfaceClass(Class<?> clazz) {
        if (IUserFacade.class.equals(clazz)) {
            return new UserFacade();
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
