package ch01;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class EchoDemo {

    public static void main(String[] args) throws Exception {
        Thread server = new Thread(() -> {
            try {
                EchoServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        server.setDaemon(true);
        server.start();

        Thread client = new Thread(() -> {
            try {
                EchoClient.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        client.setDaemon(true);
        client.start();
        client.join();
    }
}
