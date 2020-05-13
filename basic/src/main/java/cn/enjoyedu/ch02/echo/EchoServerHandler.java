package cn.enjoyedu.ch02.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext,Object msg) throws Exception{
        System.out.println("服务端读取到网络数据触发的方法channelRead");
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端读取到的数据：" + byteBuf.toString(CharsetUtil.UTF_8));
        channelHandlerContext.write(byteBuf);
        ByteBufAllocator byteBufAllocator = channelHandlerContext.alloc();
        byteBufAllocator.buffer();
        channelHandlerContext.channel().alloc();
        ByteBuf buffer = Unpooled.buffer();
        Unpooled.directBuffer();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        System.out.println("服务端读取完数据触发的方法channelReadComplete");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)           //flush掉所有的数据
            .addListener(ChannelFutureListener.CLOSE);     //当flush完后关闭连接
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext,Throwable cause) throws Exception{
        cause.printStackTrace();
        channelHandlerContext.close();
    }

}
