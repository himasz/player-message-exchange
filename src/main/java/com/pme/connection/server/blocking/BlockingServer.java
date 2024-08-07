package com.pme.connection.server.blocking;

import com.pme.connection.server.IServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The BlockingServer class implements the IServer interface and represents a
 * multi-threaded server that accepts and handles client connections using a
 * blocking I/O approach. It listens for incoming client connections on a
 * specified port and assigns each connection to a separate thread for handling using BlockingClientHandler.
 */

public class BlockingServer implements IServer {
    private final int port;
    private ServerSocket serverSocket;
    private final CopyOnWriteArrayList<Socket> clients = new CopyOnWriteArrayList<>();
    private final ExecutorService networkExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService clientsExecutor = Executors.newFixedThreadPool(2);
    private volatile boolean running;

    public BlockingServer() {
        //Assume we are getting the port from configuration file
        this(3465);
    }

    public BlockingServer(int port) {
        this.port = port;
    }

    @Override
    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Server started on port " + port);
        networkExecutor.execute(this::connectionLoop);
    }

    private void connectionLoop() {
        try {
            while (running) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client is Connected!");
                clients.add(socket);
                clientsExecutor.execute(new BlockingClientHandler(socket, serverSocket, clients));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            for (Socket player : clients) {
                player.close();
            }
            running = false;
            networkExecutor.shutdown();
            clientsExecutor.shutdown();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
