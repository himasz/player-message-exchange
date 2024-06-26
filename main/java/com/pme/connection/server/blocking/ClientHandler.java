package com.pme.connection.server.blocking;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientHandler implements Runnable {
    private final Socket socket;
    private final CopyOnWriteArrayList<Socket> players;
    int count = 0;

    public ClientHandler(Socket socket, CopyOnWriteArrayList<Socket> players) {
        this.socket = socket;
        this.players = players;
    }

    public void run() {
        try (InputStream input = socket.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            while (players.size() == 1) {
                // Wait for another player to join
            }
            Optional<Socket> optionalOtherSocket = players.stream().filter(socket1 -> socket != socket1).findFirst();
            if (optionalOtherSocket.isPresent()) {
                Socket otherSocket = optionalOtherSocket.get();
                PrintWriter otherWriter = new PrintWriter(otherSocket.getOutputStream(), true);

                String text;
                while ((text = reader.readLine()) != null && count != 10) {
                    System.out.println("Sent: " + text);
                    otherWriter.println(text + " - " + ++count);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

