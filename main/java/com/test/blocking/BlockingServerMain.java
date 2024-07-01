package com.test.blocking;

import com.pme.connection.server.blocking.BlockingServer;

import java.io.IOException;

public class BlockingServerMain {
    public static void main(String[] args) {
        try {
            BlockingServer server = new BlockingServer();
            server.startServer();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
