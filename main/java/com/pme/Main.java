package com.pme;

import com.pme.connection.server.blocking.BlockingServer;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        BlockingServer server = new BlockingServer(3455);
        server.startServer();

        Player receiver = new Player("receiver");
        Player initiator = new Player("initiator");
        for (int i = 1; i < 11; i++) {
            initiator.sendMessage("Hello");
            String receivedMessage = receiver.receiveMessage();
            System.out.println("receivedMessage: " + receivedMessage);
            initiator.sendMessage(receivedMessage);
        }
//        server.close();
    }
}
