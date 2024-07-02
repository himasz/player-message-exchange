package com.test.blocking;

import com.pme.Player;
import com.pme.connection.server.blocking.BlockingServer;

import java.io.IOException;

/**
 * The BlockingMain class is the entry point for running a blocking I/O example.
 * It starts a blocking server, creates two player instances that communicate with each other
 * through the server, and demonstrates sending and receiving messages between the players.
 */
public class BlockingMain {
    public static void main(String[] args) {
        try {
            System.out.println("Run the blocking example: ");
            BlockingServer server = new BlockingServer();
            server.startServer();

            Player initiator = new Player("initiator");
            initiator.connect();
            Player other = new Player("other");
            other.connect();
            String message = "";
            for (int i = 1; i < 11; i++) {
                initiator.sendMessage(!message.isEmpty() ? message : "Hello");
                message = other.receiveMessage();
                System.out.println("Other received: " + message);
                other.sendMessage(message);
                message = initiator.receiveMessage();
                System.out.println("Initiator received: " + message);
            }
            initiator.done();
            other.done();
            server.close();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
