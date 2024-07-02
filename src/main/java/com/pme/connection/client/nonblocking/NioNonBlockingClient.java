package com.pme.connection.client.nonblocking;

import com.pme.connection.client.IClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * The NioNonBlockingClient class implements the IClient interface and represents a client
 * that connects to a server using non-blocking I/O operations with Java NIO (New Input/Output).
 * It facilitates sending and receiving messages to and from the server over a non-blocking socket connection.
 */
public class NioNonBlockingClient implements IClient {
    /**
     * The port number of the server to which the client connects.
     */
    private final int port;

    /**
     * The hostname or IP address of the server to which the client connects.
     */
    private final String hostname;

    /**
     * The non-blocking socket channel used for the connection to the server.
     */
    private SocketChannel socketChannel;

    public NioNonBlockingClient() {
        this("localhost", 3465);
    }

    public NioNonBlockingClient(String hostname, int port) {
        this.port = port;
        this.hostname = hostname;
    }

    public void startConnection() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(hostname, port));

        while (!socketChannel.finishConnect()) {
            // Wait until the connection is established
        }

        System.out.println("New client connected: " + socketChannel.getRemoteAddress());
    }


    @Override
    public void sendMessage(String message) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.allocate(256);
        writeBuffer.put(message.getBytes());
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()) {
            socketChannel.write(writeBuffer);
        }
    }

    @Override
    public String receiveMessage() throws IOException {
        ByteBuffer readBuffer = ByteBuffer.allocate(256);
        int bytesRead = socketChannel.read(readBuffer);
        while (bytesRead == 0) {
            bytesRead = socketChannel.read(readBuffer);
        }
        if (bytesRead != -1) {
            readBuffer.flip();
            return new String(readBuffer.array()).trim();
        }
        return "";
    }


    @Override
    public void close() throws IOException {
        socketChannel.close();
    }
}
