package demo.server1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * NettyServer1 initializer
 */
public class Server1Initializer extends ChannelInitializer<NioSocketChannel> {

    private static final int READ_TIMEOUT_SEC = 600;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addFirst(new ReadTimeoutHandler(READ_TIMEOUT_SEC));
        ch.pipeline().addLast(new LengthFieldPrepender(4));
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

    }
}
