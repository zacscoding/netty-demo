package ch04.inout;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class InOutEchoServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
            .addLast(new InOutEchoServerInboundHandler())
            .addLast(new InOutEchoServerOutboundHandler())
        ;
    }
}
