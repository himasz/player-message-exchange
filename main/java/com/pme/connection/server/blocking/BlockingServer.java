package com.pme.connection.server.blocking;

import com.pme.connection.server.IServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlockingServer implements IServer {
    private final int port;
    private ServerSocket serverSocket;
    private final CopyOnWriteArrayList<Socket> clients = new CopyOnWriteArrayList<>();
    Executor networkExecutor = Executors.newSingleThreadExecutor();
    Executor clientsExecutor = Executors.newFixedThreadPool(2);
    private boolean running = true;

    public BlockingServer() {
        //Assume we are getting the port from configuration file
        this(3455);
    }

    public BlockingServer(int port) {
        this.port = port;
    }

    @Override
    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        networkExecutor.execute(() -> {
            try {
                connectionLoop();
            } catch (IOException e) {
                //TODO: check of return it to the client
                System.out.println(e.getMessage());
            }
        });
    }

    private void connectionLoop() throws IOException {
        while (running) {
            Socket socket = serverSocket.accept();
            System.out.println("New Client is Connected!");
            clients.add(socket);
            clientsExecutor.execute(new BlockingClientHandler(socket, clients));
        }
    }

    @Override
    public void close() {
        try {
            for (Socket player : clients) {
                player.close();
            }
            running = false;
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
