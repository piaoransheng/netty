package cn.enjoyedu.ch02.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        //线程组
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); //服务端启动必备
            serverBootstrap.group(group,work)
                .channel(NioServerSocketChannel.class)  //指明用nio进行通讯
                    .localAddress(new InetSocketAddress(port)) //指明服务器监听的窗口
                    .childOption(ChannelOption.TCP_NODELAY,true)  //接收到连接请求，新启一个socket通信，也就是channel，每个channel有自己的事件handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
                ChannelFuture f = serverBootstrap.bind().sync();  //绑定到端口，阻塞等待直到连接完成
                f.channel().closeFuture().sync();                //阻塞直到channel
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 9999;
        EchoServer echoServer = new EchoServer(port);
        echoServer.start();
    }
}
