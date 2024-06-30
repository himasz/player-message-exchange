package com.pme.connection.server.nonblocking;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class NonBlockingClientHandler implements Runnable {
    private final SocketChannel socketChannel;
    private final CopyOnWriteArrayList<SocketChannel> clients;
    private final SelectionKey key;
    int count = 0;

    public NonBlockingClientHandler(SelectionKey key, SocketChannel socketChannel, CopyOnWriteArrayList<SocketChannel> clients) {
        this.key = key;
        this.socketChannel = socketChannel;
        this.clients = clients;
    }

    public void run() {
        while (clients.size() == 1) {
            // Wait for another player to join
        }
        Optional<SocketChannel> otherOptional = clients.stream().filter(socketChannel1 -> socketChannel != socketChannel1).findFirst();
        if (otherOptional.isPresent()) {
            SocketChannel otherSocketChannel = otherOptional.get();
            try {
                ByteBuffer readBuffer = ByteBuffer.allocate(256);
                ByteBuffer writeBuffer = ByteBuffer.allocate(256);
                while (count < 10 && socketChannel.isConnected()) {
                    readBuffer.clear();
                    int numRead = socketChannel.read(readBuffer);

                    if (numRead > 0) {
                        readBuffer.flip();
                        byte[] data = new byte[numRead];
                        readBuffer.get(data);
                        String message = new String(data).trim();
                        System.out.println("Server: " + message);

                        message += " - " + ++count;
                        writeBuffer.clear();
                        writeBuffer.put(message.getBytes());
                        writeBuffer.flip();
                        otherSocketChannel.write(writeBuffer);
                    } else if (numRead == -1) {
                        socketChannel.close();
                        key.cancel();
                        System.out.println("Client disconnected");
                        return;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socketChannel.close();
                    otherSocketChannel.close();
                    key.cancel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        }
    }
}
