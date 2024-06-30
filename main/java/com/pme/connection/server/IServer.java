package com.pme.connection.server;

import java.io.IOException;

public interface IServer {
    void startServer() throws IOException;

    void close() throws IOException;
}
