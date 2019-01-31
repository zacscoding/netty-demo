package ch01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

/**
 * https://github.com/krisjey/netty.book.kor
 */
@Slf4j(topic = "echo-client")
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    // 소켓 채널이 최초 활성화 되었을 때
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sendMessage = "Hello, Netty!";

        ByteBuf messageBuffer = Unpooled.buffer();
        messageBuffer.writeBytes(sendMessage.getBytes());

        logger.info("Sended message : {}", sendMessage);

        // 데이터 기록(write) And 전송(flush)
        ctx.writeAndFlush(messageBuffer);
    }

    // server로 부터 수신된 데이터가 있을 때 호출되는 네티 이벤트
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Receive message...");
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
        logger.info(">> " + readMessage);
    }

    // 수신된 데이터가 모두 읽었을 때 호출되는 이벤트 메소드
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("Complete to read messages. will close channel");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Exception occur while handle EchoClient", cause);
        ctx.close();
    }
}
