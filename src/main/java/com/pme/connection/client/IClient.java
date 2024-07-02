package com.pme.connection.client;

import java.io.IOException;

/**
 * The IClient interface defines the basic operations that a client should implement.
 * It includes methods for starting a connection, sending messages, receiving messages,
 * and closing the connection.
 */
public interface IClient {

    /**
     * Starts the connection to the server.
     * This method should be implemented to include the logic for initializing
     * the client and establishing a connection to the server.
     *
     * @throws IOException if an I/O error occurs while starting the connection
     */
    void startConnection() throws IOException;

    /**
     * Sends a message to the server.
     * This method should be implemented to include the logic for sending a message
     * to the server over the established connection.
     *
     * @param text the message to be sent to the server
     * @throws IOException if an I/O error occurs while sending the message
     */
    void sendMessage(String text) throws IOException;

    /**
     * Receives a message from the server.
     * This method should be implemented to include the logic for receiving a message
     * from the server over the established connection.
     *
     * @return the message received from the server
     * @throws IOException if an I/O error occurs while receiving the message
     */
    String receiveMessage() throws IOException;

    /**
     * Closes the connection to the server and releases any resources associated with it.
     * This method should be implemented to include the logic for gracefully
     * shutting down the client connection and cleaning up any resources.
     *
     * @throws IOException if an I/O error occurs while closing the connection
     */
    void close() throws IOException;
}