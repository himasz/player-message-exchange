package com.test.nonblocking;

import com.pme.connection.server.nonblocking.NioNonBlockingServer;

import java.io.IOException;

public class NonBlockingServerMain {
    public static void main(String[] args) {
        NioNonBlockingServer server = new NioNonBlockingServer();
        try {
            server.startServer();
        } catch (IOException e) {
            server.close();
        }
    }
}
