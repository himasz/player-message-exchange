package com.test.blocking;

import com.pme.Player;

import java.io.IOException;

public class InitiatorPlayerMain {
    public static void main(String[] args) {
        Player initiator = new Player("initiator");
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
