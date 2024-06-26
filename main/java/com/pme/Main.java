package com.pme;

import com.pme.connection.server.blocking.BlockingServer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingServer server = new BlockingServer();
        server.startServer();

        Player receiver = new Player("receiver");
        Player initiator = new Player("initiator");
        String receivedMessage = "";
        for (int i = 1; i < 11; i++) {
            initiator.sendMessage(!"".equals(receivedMessage) ? receivedMessage : "Hello");
            receivedMessage = receiver.receiveMessage();
            System.out.println("Reply: " + receivedMessage);
            Thread.sleep(100);
        }
        initiator.done();
        receiver.done();
        server.close();
        System.exit(0);
    }
}
