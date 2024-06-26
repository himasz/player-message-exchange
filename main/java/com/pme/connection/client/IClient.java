package com.pme.connection.client;

public interface IClient {
    void sendMessage(String text);

    String receiveMessage();

    void close();

}
