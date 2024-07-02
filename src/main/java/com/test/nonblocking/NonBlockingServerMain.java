package com.test.nonblocking;

import com.pme.connection.server.nonblocking.NioNonBlockingServer;

import java.io.IOException;

public class NonBlockingServerMain {
    public static void main(String[] args) {
        try {
            NioNonBlockingServer server = new NioNonBlockingServer();
            server.startServer();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
