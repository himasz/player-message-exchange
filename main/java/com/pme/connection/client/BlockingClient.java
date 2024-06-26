package com.pme.connection.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class BlockingClient implements IClient {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private int count = 0;

    public BlockingClient() {
        //Assume we are getting the values from configuration file
        this("localhost", 3455);
    }

    public BlockingClient(String hostname, int port) {
        try {
            this.socket = new Socket(hostname, port);

            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    @Override
    public void sendMessage(String message) {
        if (10 != count++) {
            writer.println(message);
        } else {
            close();
        }
    }

    @Override
    public String receiveMessage() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
