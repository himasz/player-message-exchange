package com.test.blocking;

import com.pme.Player;

import java.io.IOException;

public class OtherPlayerMain {
    public static void main(String[] args) {
        Player other = new Player("other");
        try {
            other.connect();
            String receivedMessage;
            for (int i = 1; i < 11; i++) {
                receivedMessage = other.receiveMessage();
                System.out.println("Other received: " +receivedMessage);
                other.sendMessage(receivedMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            other.done();
        }
    }
}
