package com.pme.connection.client;

public interface IClient {
    public void sendMessage(String text);

    public String receiveMessage();

    public void close();

}
