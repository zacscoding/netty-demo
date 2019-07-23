package ch04.customcodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.nio.charset.Charset;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Slf4j(topic = "Decoder")
public class LowercaseDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf byteBuf = in.readBytes(in.readableBytes());
        String read = byteBuf.toString(Charset.defaultCharset());
        String decoded = read == null ? "null" : read.toLowerCase();
        logger.info("decode is called. read : {} > decoded : {}", read, decoded);
        out.add(decoded);
    }
}
