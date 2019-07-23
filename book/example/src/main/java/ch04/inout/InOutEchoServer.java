package ch04.inout;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class InOutEchoServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new InOutEchoServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
//        1)
//        ch.pipeline()
//            .addLast(new InOutEchoServerOutboundHandler())
//            .addLast(new InOutEchoServerInboundHandler())
//        ;
//        00:24:12.654 INFO [nioEventLoopGroup-3-1] [Inbound] channelRead is called.. : a
//        00:24:12.654 INFO [nioEventLoopGroup-3-1] [Outbound] write is called
//        00:24:12.655 INFO [nioEventLoopGroup-3-1] [Outbound] flush is called
//        00:24:12.657 INFO [nioEventLoopGroup-3-1] [Inbound] channelReadComplete is called..
//        00:24:12.657 INFO [nioEventLoopGroup-3-1] [Outbound] read is called

//        2)
//        ch.pipeline()
//            .addLast(new InOutEchoServerInboundHandler())
//            .addLast(new InOutEchoServerOutboundHandler())
//        ;
//        00:25:43.935 INFO [nioEventLoopGroup-3-1] [Inbound] channelRead is called.. : a
//        00:25:43.936 INFO [nioEventLoopGroup-3-1] [Inbound] channelReadComplete is called..
//        00:25:43.936 INFO [nioEventLoopGroup-3-1] [Outbound] read is called
    }

}
