package com.test.nonblocking;

import com.pme.Player;
import com.pme.connection.client.nonblocking.NioNonBlockingClient;

import java.io.IOException;

public class NonBlockingInitiatorPlayerMain {
    public static void main(String[] args) {
        Player initiator = new Player("initiator", new NioNonBlockingClient());
        try {
            initiator.connect();
            String receivedMessage = "";
            for (int i = 1; i < 11; i++) {
                initiator.sendMessage(!receivedMessage.isEmpty() ? receivedMessage : "Hello");
                receivedMessage = initiator.receiveMessage();
                System.out.println("initiator received: " + receivedMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            initiator.done();
        }
    }
}
