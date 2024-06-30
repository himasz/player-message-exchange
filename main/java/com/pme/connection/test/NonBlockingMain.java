package com.pme.connection.test;

import com.pme.Player;
import com.pme.connection.client.nonblocking.NioNonBlockingClient;
import com.pme.connection.server.IServer;
import com.pme.connection.server.nonblocking.NioNoneBlockingServer;

import java.io.IOException;

public class NonBlockingMain {
    public static void main(String[] args) {
        try {
            IServer server = new NioNoneBlockingServer();
            server.startServer();
            Player initiator = new Player("initiator", new NioNonBlockingClient());
            initiator.connect();
            Player receiver = new Player("receiver", new NioNonBlockingClient());
            receiver.connect();
            String receivedMessage = "";
            for (int i = 1; i < 11; i++) {
                initiator.sendMessage(!receivedMessage.isEmpty() ? receivedMessage : "Hello");
                receivedMessage = receiver.receiveMessage();
                while (receivedMessage == null || receivedMessage.isBlank()) {
                    receivedMessage = receiver.receiveMessage();
                }
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
