package ch02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

/**
 * https://github.com/krisjey/netty.book.kor
 */
@Slf4j
public class BlockingServer {

    public static void main(String[] args) throws IOException {
        new BlockingServer().run();
    }

    private void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        logger.info("서버 접속 대기중... >> {}",
            (serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort()));

        while (!Thread.currentThread().isInterrupted()) {
            Socket socket = serverSocket.accept();
            logger.info("클라이언트 연결됨.. : {}", socket.getInetAddress());

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            while (true) {
                try {
                    // 데이터 수신을 기다리면서 스레드가 블로킹 됨.
                    logger.info("데이터 수신 대기 중..");
                    int request = in.read();
                    out.write(request);
                } catch (IOException e) {
                    break;
                }
            }
        }

        serverSocket.close();
    }

}
