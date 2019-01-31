package ch01;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * https://github.com/krisjey/netty.book.kor
 */
@Slf4j(topic = "discard")
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("channelRead0 is called.. ctx : {} / object : {}", channelHandlerContext, o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("exception occur", cause);
        ctx.close();
    }
}
