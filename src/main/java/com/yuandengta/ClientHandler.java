package com.yuandengta;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @Author:Hardy
 * @QQ:2937270766
 * @官网：http://www.yuandengta.com
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当客户端和服务端 TCP 链路建立成功之后，Netty 的 NIO 线程会调用 channelActive 方法
     *
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String reqMsg = "你好，Server,我是client端";
            ByteBuf buf = ctx.alloc().buffer();
            byte[] b = reqMsg.getBytes("UTF-8");
            buf.writeBytes(b);
            ctx.channel().writeAndFlush(buf);


    }

    /**
     * 当服务端返回应答消息时，channelRead 方法被调用，从 Netty 的 ByteBuf 中读取并打印应答消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Server return Message：" + System.currentTimeMillis());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**释放资源*/
        ctx.close();
    }
}
