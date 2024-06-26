package com.pme.connection.server.blocking;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientHandler {
    private Socket socket;
    private final CopyOnWriteArrayList<Socket> players;
    int count = 0;

    public ClientHandler(Socket socket, CopyOnWriteArrayList<Socket> players) {
        this.socket = socket;
        this.players = players;
    }

    public void run() {
        try (
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        ) {
            for (Socket player : players) {
                if (player != socket) {
                    OutputStream output = player.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);

                    String text;

                    if ((text = reader.readLine()) != null && count != 10) {
                        System.out.println("Received: " + text);
                        writer.println(text + " - " + ++count);
                    }

                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        finally {
//            try {
//                socket.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    }
}

