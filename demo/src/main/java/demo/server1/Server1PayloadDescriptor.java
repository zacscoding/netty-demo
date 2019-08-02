package demo.server1;

/**
 *
 */
public enum Server1PayloadDescriptor {

    PING(1), PONG(2);

    private int value;

    Server1PayloadDescriptor(int value) {
        this.value = value;
    }
}
