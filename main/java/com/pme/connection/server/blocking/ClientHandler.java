package com.pme.connection.server.blocking;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientHandler {
    private final Socket socket;
    private final CopyOnWriteArrayList<Socket> players;
    int count = 0;

    public ClientHandler(Socket socket, CopyOnWriteArrayList<Socket> players) {
        this.socket = socket;
        this.players = players;
    }

    public void run() {
        try (
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input))
        ) {
            while (players.size() == 1) {
            }
            Optional<Socket> optionalOtherSocket = players.stream().filter(socket1 -> socket != socket1).findFirst();
            if (optionalOtherSocket.isPresent()) {
                Socket otherSocket = optionalOtherSocket.get();
                OutputStream output = otherSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String text;

                while ((text = reader.readLine()) != null && count != 10) {
                    System.out.println("Received: " + text);
                    writer.println(text + " - " + ++count);

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

