package ch02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * https://github.com/krisjey/netty.book.kor
 */
@Slf4j
public class NonBlockingServer {

    public static void main(String[] args) {
        new NonBlockingServer().startEchoServer();
    }

    private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
    private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);

    private void startEchoServer() {
        try (Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            if ((serverSocketChannel.isOpen()) && (selector.isOpen())) {
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress(8888));

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                logger.info("접속 대기중..");

                while (true) {
                    // selector에 등록된 채널에 변경 사항이 발생했는지 검사. 여기서 블로킹 됨
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove(); // 동일한 이벤트가 감지되는 것 피하기 위해 제거

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            acceptOP(key, selector);
                        } else if (key.isReadable()) {
                            readOP(key);
                        } else if (key.isWritable()) {
                            writeOP(key);
                        }
                    }
                }
            } else {
                logger.warn("서버 소켓 생성 실패");
            }
        } catch (IOException e) {
            logger.warn("IOException occur while running echo server", e);
        }
    }

    private void acceptOP(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        logger.info("클라이언트 연결 됨 :: " + socketChannel.getRemoteAddress());

        keepDataTrack.put(socketChannel, new ArrayList<>());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void readOP(SelectionKey key) {
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            buffer.clear();
            int numRead = -1;
            try {
                numRead = socketChannel.read(buffer);
            } catch (IOException e) {
                logger.warn("데이터 읽기 오류!");
            }

            if (numRead == -1) {
                this.keepDataTrack.remove(socketChannel);
                logger.info("클라이언트 연결 종료 : {}", socketChannel.getRemoteAddress());
                socketChannel.close();
                key.cancel();
                return;
            }

            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            logger.info("{} from {}", new String(data, "UTF-8"), socketChannel.getRemoteAddress());

            doEchoJob(key, data);
        } catch (IOException e) {
            logger.warn("IOException occur", e);
        }
    }

    private void writeOP(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        Iterator<byte[]> iterator = channelData.iterator();

        while (iterator.hasNext()) {
            byte[] it = iterator.next();
            iterator.remove();
            socketChannel.write(ByteBuffer.wrap(it));
        }

        key.interestOps(SelectionKey.OP_READ);
    }

    private void doEchoJob(SelectionKey key, byte[] data) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        channelData.add(data);

        key.interestOps(SelectionKey.OP_WRITE);
    }
}
