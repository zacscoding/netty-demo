package demo.server1;

import lombok.Getter;

/**
 *
 */
@Getter
public class Server1MessageHeader {

    private String id;
    private Server1PayloadDescriptor descriptor;
    private int ttl;
    private int payloadLength;
}
