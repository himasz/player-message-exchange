package com.pme;

import com.pme.connection.client.BlockingClient;

import java.io.*;

public class Player {
    private final BlockingClient channel;
    private String name;

    public Player(String name) {
        this.name = name;
        this.channel = new BlockingClient();
    }


    public void sendMessage(String message) {
        channel.sendMessage(message);
    }

    public String receiveMessage() {
        return channel.receiveMessage();
    }
}
