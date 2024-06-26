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
            Thread.sleep(10);
            Player other = new Player("other", new NioNonBlockingClient());
            other.connect();

            String message = "";
            for (int i = 1; i < 11; i++) {
                initiator.sendMessage(!message.isEmpty() ? message : "Hello");
                message = other.receiveMessage();
                while (message == null || message.isBlank()) {
                    message = other.receiveMessage();
                }
                System.out.println("Other received: " + message);
                other.sendMessage(message);
                message = initiator.receiveMessage();
//                while (message == null || message.isBlank()) {
//                    message = initiator.receiveMessage();
//                }
                System.out.println("Initiator received: " + message);
            }
            initiator.done();
            other.done();
            server.close();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
