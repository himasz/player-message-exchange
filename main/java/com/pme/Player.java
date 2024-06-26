package com.pme;

import com.pme.connection.client.BlockingClient;
import com.pme.connection.client.IClient;

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
