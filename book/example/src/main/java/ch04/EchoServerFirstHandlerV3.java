package ch04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j(topic = "FirstHandler")
public class EchoServerFirstHandlerV3 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf readMessage = (ByteBuf) msg;
        logger.info("channelRead : {}", readMessage.toString(Charset.defaultCharset()));
        ctx.write(msg);
        // 이벤트 1개당 핸들러 한번이므로 다음 핸들러를 실행하기 위해 fireXX() 호출
        ctx.fireChannelRead(msg);
    }
}
