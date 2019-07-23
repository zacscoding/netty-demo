package ch04.customcodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 * Uppercase codec
 */
@Slf4j(topic = "UppercaseCodec")
public class UppercaseCodec extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        String encoded = msg == null ? "" : msg.toUpperCase();
        logger.info("encode is called... msg : {} >> encode {}", msg, encoded);
        out.writeCharSequence(encoded, Charset.defaultCharset());
    }
}
