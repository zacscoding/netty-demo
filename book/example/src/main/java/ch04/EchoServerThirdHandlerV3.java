package ch04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j(topic = "ThirdHandler")
public class EchoServerThirdHandlerV3 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf readMessage = (ByteBuf) msg;
        logger.info("channelRead : {}", readMessage.toString(Charset.defaultCharset()));
    }
}
