package ch04.customcodec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class EchoWithCodecServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LowercaseDecoder());
        pipeline.addLast(new UppercaseEncoder());

        pipeline.addLast(new EchoWithCodecServerHandler());
    }
}
