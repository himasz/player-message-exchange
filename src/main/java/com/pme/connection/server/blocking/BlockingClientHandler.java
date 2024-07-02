package com.pme.connection.server.blocking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The BlockingClientHandler class implements the Runnable interface and is responsible
 * for handling client connections to a server. Each instance of this class handles
 * communication with a single client in a separate thread using blocking I/O operations.
 */
public class BlockingClientHandler implements Runnable {
    private final Socket socket;
    private final ServerSocket serverSocket;
    private final CopyOnWriteArrayList<Socket> clients;
    int count = 0;

    public BlockingClientHandler(Socket socket, ServerSocket serverSocket, CopyOnWriteArrayList<Socket> clients) {
        this.socket = socket;
        this.clients = clients;
        this.serverSocket = serverSocket;
    }

    public void run() {
        Socket otherSocket = null;
        try (InputStream input = socket.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            while (clients.size() == 1) {
                // Wait for another player to join
            }
            Optional<Socket> optionalOtherSocket = clients.stream().filter(socket1 -> socket != socket1).findFirst();
            if (optionalOtherSocket.isPresent()) {
                otherSocket = optionalOtherSocket.get();
                PrintWriter otherWriter = new PrintWriter(otherSocket.getOutputStream(), true);

                String text;
                while ((text = reader.readLine()) != null && count != 10) {
                    System.out.println("Server: " + text);
                    otherWriter.println(text + " - " + ++count);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                socket.close();
                if (socket.isClosed() && otherSocket != null && otherSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

