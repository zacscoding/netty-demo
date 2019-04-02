package ch04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Slf4j
public class EchoServerHandlerV2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
        logger.info("receive message : {}", readMessage);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete() is called");
        ctx.flush();
    }
}