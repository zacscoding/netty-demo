package ch04.customcodec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class EchoWithCodecServer {

    static final int PORT = 8888;

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new EchoWithCodecServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();

//            00:37:40.983 INFO [nioEventLoopGroup-3-1] [Decoder] decode is called. read : A > decoded : a
//            00:37:40.983 INFO [nioEventLoopGroup-3-1] [Handler] channelRead0 is called.. msg : a
//            00:37:40.983 INFO [nioEventLoopGroup-3-1] [Encoder] encode is called.. msg : a >> encoded : A
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
