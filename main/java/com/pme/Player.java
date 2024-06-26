package com.pme;

import com.pme.connection.client.BlockingClient;

public class Player {
    private final BlockingClient channel;
    private final String name;

    public Player(String name) {
        this.name = name;
        this.channel = new BlockingClient();
    }

    public Player(String name, String hostname, int port) {
        this.name = name;
        this.channel = new BlockingClient(hostname, port);
    }


    public void sendMessage(String message) {
        channel.sendMessage(message);
    }

    public String receiveMessage() {
        return channel.receiveMessage();
    }

    public void done() {
        this.channel.close();
    }
}
