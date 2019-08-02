package demo.server1;

import demo.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class Server1 implements Server {

    private int port;
    private Thread server;

    public Server1(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void start() {
        if (server != null && server.isAlive()) {
            logger.warn("NettyServer1 is already running.");
            return;
        }

        server = new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                ServerBootstrap b = new ServerBootstrap();

                b.group(bossGroup, workerGroup);
                b.channel(NioServerSocketChannel.class);

                b.handler(new LoggingHandler());
                b.childHandler(new Server1Initializer());

                ChannelFuture channelFuture = b.bind(Server1.this.port).sync();
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                logger.warn("Exception occur while running NettyServer1", e);
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        });
    }

    @Override
    public void stop() {
        if (server != null && server.isAlive()) {
            server.interrupt();
        }

        server = null;
    }
}
