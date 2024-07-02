package com.pme;

import com.pme.connection.client.blocking.BlockingClient;
import com.pme.connection.client.IClient;

import java.io.IOException;

public class Player {
    private final String name;
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
