package ch04.customcodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Slf4j(topic = "Encoder")
public class UppercaseEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        String encoded = msg == null ? "NULL" : msg.toUpperCase();
        logger.info("encode is called.. msg : {} >> encoded : {}", msg, encoded);
        out.writeCharSequence(encoded, Charset.defaultCharset());
    }
}
