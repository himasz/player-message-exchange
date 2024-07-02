package com.test.nonblocking;

import com.pme.Player;
import com.pme.connection.client.nonblocking.NioNonBlockingClient;

import java.io.IOException;

public class NonBlockingOtherPlayerMain {
    public static void main(String[] args) {
        Player other = new Player("other", new NioNonBlockingClient());
        try {
            other.connect();
            String receivedMessage;
            for (int i = 1; i < 11; i++) {
                receivedMessage = other.receiveMessage();
                System.out.println("Other received: " + receivedMessage);
                other.sendMessage(receivedMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            other.done();
        }
    }
}
