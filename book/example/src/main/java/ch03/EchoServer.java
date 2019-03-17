package ch03;

import ch01.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class EchoServer {

    public static void main(String[] args) throws Exception {
        // 단일 스레드로 동작하는 NioEventLoopGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // CPU 코어 수에 따른 스레드로 동작하는 NioEventLoopGroup
        // Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2))
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // bossGroup : 부모 스레드 (클라이언트 연결 요청 수락 담당)
            // workerGroup : 연결된 소켓에 대한 I/O 처리를 담당하는 자식 스레드
            serverBootstrap.group(bossGroup, workerGroup);
            // 서버소켓(부모 스레드)이 사용할 네트워크 입출력 모드 (여기서는 NIO)
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 자식 채널의 초기화 방법을 설정
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // 채널 파이프라인 객체 생성
                    ChannelPipeline channelPipeline = socketChannel.pipeline();
                    // 채널 파이프라인에 EchoServerHandler를 등록
                    channelPipeline.addLast(new EchoServerHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
