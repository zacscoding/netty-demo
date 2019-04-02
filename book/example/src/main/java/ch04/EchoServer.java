package ch04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class EchoServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 클라이언트 소켓 채널이 생성될 떄 호출
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        // ChannelInboundHandlerAdapter 메소드 호출 순서 체크
                        // p.addLast(new EchoServerHandler());
                        // p.addLast(new EchoServerHandlerV1());
                        p.addLast(new EchoServerHandlerV2());
                    }
                });

            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        /*
        telnet localhost 8888
        h

        23:01:09.943 INFO [nioEventLoopGroup-3-1] [ChannelEventHandler] channelRegistered() is called
        23:01:09.943 INFO [nioEventLoopGroup-3-1] [ChannelEventHandler] channelActive() is called
        23:01:11.218 INFO [nioEventLoopGroup-3-1] [ChannelEventHandler] receive message : h
        23:01:11.219 INFO [nioEventLoopGroup-3-1] [ChannelEventHandler] channelRead() is called
        23:01:11.219 INFO [nioEventLoopGroup-3-1] [ChannelEventHandler] channelReadComplete() is called
        23:02:35.240 INFO [nioEventLoopGroup-3-1] [ChannelEventHandler] channelUnregistered() is called
         */
    }
}
