package com.pme.connection.test;

import com.pme.Player;
import com.pme.connection.server.blocking.BlockingServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BlockingServer server = new BlockingServer();
            server.startServer();

            Player initiator = new Player("initiator");
            initiator.connect();
            Player receiver = new Player("receiver");
            receiver.connect();
            String receivedMessage = "";
            for (int i = 1; i < 11; i++) {
                initiator.sendMessage(!receivedMessage.isEmpty() ? receivedMessage : "Hello");
                receivedMessage = receiver.receiveMessage();
                System.out.println("Reply: " + receivedMessage);
                receivedMessage += " - " + i;
            }
            initiator.done();
            receiver.done();
            server.close();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
