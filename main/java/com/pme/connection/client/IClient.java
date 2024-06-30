package com.pme.connection.client;

import java.io.IOException;

public interface IClient {
    void startConnection() throws IOException;

    void sendMessage(String text) throws IOException;

    String receiveMessage() throws IOException;

    void close() throws IOException;

}
