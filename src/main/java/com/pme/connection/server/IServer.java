package com.pme.connection.server;

import java.io.IOException;

/**
 * The IServer interface defines the basic operations that a server should implement.
 * It includes methods for starting and closing the server.
 */
public interface IServer {

    /**
     * Starts the server and begins listening for incoming client connections.
     * This method should be implemented to include the logic for initializing
     * the server and handling client connections.
     *
     * @throws IOException if an I/O error occurs while starting the server
     */
    void startServer() throws IOException;

    /**
     * Closes the server and releases any resources associated with it.
     * This method should be implemented to include the logic for gracefully
     * shutting down the server and closing any open connections.
     *
     * @throws IOException if an I/O error occurs while closing the server
     */
    void close() throws IOException;
}
