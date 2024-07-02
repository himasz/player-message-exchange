package com.pme.connection.client.blocking;

import com.pme.connection.client.IClient;

import java.io.*;
import java.net.Socket;

/**
 * The BlockingClient class implements the IClient interface and represents a client
 * that connects to a server using blocking I/O operations. It facilitates sending
 * and receiving messages to and from the server over a socket connection.
 */
public class BlockingClient implements IClient {
    /**
     * The port number of the server to which the client connects.
     */
    private final int port;

    /**
     * The hostname or IP address of the server to which the client connects.
     */
    private final String hostname;

    /**
     * The socket used for the connection to the server.
     */
    private Socket socket;

    /**
     * The writer used for sending messages to the server.
     */
    private PrintWriter writer;

    /**
     * The reader used for receiving messages from the server.
     */
    private BufferedReader reader;

    public BlockingClient() {
        //Assume we are getting the values from configuration file
        this("localhost", 3465);
    }

    public BlockingClient(String hostname, int port) {
        this.port = port;
        this.hostname = hostname;
    }

    @Override
    public void startConnection() throws IOException {
        this.socket = new Socket(hostname, port);
        OutputStream output = socket.getOutputStream();
        this.writer = new PrintWriter(output, true);

        InputStream input = socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public void sendMessage(String message) {
        writer.println(message);
    }

    @Override
    public String receiveMessage() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
