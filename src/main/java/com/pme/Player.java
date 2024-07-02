package com.pme;

import com.pme.connection.client.blocking.BlockingClient;
import com.pme.connection.client.IClient;

import java.io.IOException;

/**
 * The Player class represents a player who can connect to a server,
 * send messages to the server, receive messages from the other player connected to the  server, and close the connection.
 * It uses an IClient implementation to manage the connection and communication with the server.
 */
public class Player {
    /**
     * The name of the player.
     */
    private final String name;

    /**
     * The client channel used for communication with the server.
     */
    private final IClient channel;

    public Player(String name) {
        this.name = name;
        this.channel = new BlockingClient();
    }

    public Player(String name, IClient channel) {
        this.name = name;
        this.channel = channel;
    }

    public void connect() throws IOException {
        channel.startConnection();
    }

    public void sendMessage(String message) {
        try {
            channel.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String receiveMessage() {
        try {
            return channel.receiveMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void done() {
        try {
            this.channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
