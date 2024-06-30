package com.pme.connection.client.blocking;

import com.pme.connection.client.IClient;

import java.io.*;
import java.net.Socket;

public class BlockingClient implements IClient {
    private final int port;
    private final String hostname;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public BlockingClient() {
        //Assume we are getting the values from configuration file
        this("localhost", 3455);
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
