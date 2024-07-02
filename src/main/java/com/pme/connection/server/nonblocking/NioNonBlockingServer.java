package com.pme.connection.server.nonblocking;

import com.pme.connection.server.IServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The NioNonBlockingServer class implements the IServer interface and represents a
 * non-blocking I/O server using Java NIO (New Input/Output). This server is designed
 * to handle multiple client connections concurrently without blocking operations.
 * It uses a single thread to handle I/O operations.
 */
public class NioNonBlockingServer implements IServer {
    private final int port;
    private Selector selector;
    private ServerSocketChannel serverChannel;
    Executor networkExecutor = Executors.newSingleThreadExecutor();
    private volatile boolean running = true;
    private SelectionKey serverKey;
    private final CopyOnWriteArrayList<SocketChannel> clients = new CopyOnWriteArrayList<>();

    public NioNonBlockingServer() {
        this(3465);
    }

    public NioNonBlockingServer(int port) {
        this.port = port;
    }

    @Override
    public void startServer() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        running = true;
        System.out.println("Server started on port " + serverChannel.socket().getLocalPort());

        networkExecutor.execute(this::connectionLoop);
    }

    private void connectionLoop() {
        try {
            while (running) {
                selector.select(); // Block until events are available
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        Optional<SocketChannel> otherOptional = clients.stream().filter(socketChannel1 -> socketChannel != socketChannel1).findFirst();
                        otherOptional.ifPresent(otherChannel -> handleClient(otherChannel, socketChannel, key));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        closeAll();
    }

    private void handleClient(SocketChannel otherSocketChannel, SocketChannel socketChannel, SelectionKey key) {
        try {
            Integer counter = (Integer) key.attachment();
            if (counter == null) {
                counter = 1;
            }
            if (counter == 11) {
                socketChannel.close();
                if (socketChannel.socket().isClosed() && otherSocketChannel.socket().isClosed()) {
                    serverChannel.close();
                    serverKey.cancel();
                }
                return;
            }
            ByteBuffer readBuffer = ByteBuffer.allocate(256);
            ByteBuffer writeBuffer = ByteBuffer.allocate(256);
            readBuffer.clear();
            int numRead = socketChannel.read(readBuffer);

            if (numRead > 0) {
                readBuffer.flip();
                byte[] data = new byte[numRead];
                readBuffer.get(data);
                String message = new String(data).trim();
                System.out.println("Server: " + message);

                message += " - " + counter;
                key.attach(counter + 1);
                writeBuffer.clear();
                writeBuffer.put(message.getBytes());
                writeBuffer.flip();
                otherSocketChannel.write(writeBuffer);
            } else if (numRead == -1) {
                socketChannel.close();
                key.cancel();
                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        clients.add(socketChannel);
    }

    @Override
    public void close() {
        running = false;
        selector.wakeup(); // Interrupts the select() call
    }

    private void closeAll() {
        try {
            for (SelectionKey key : selector.keys()) {
                if (key.channel() instanceof SocketChannel) {
                    key.channel().close();
                }
            }
            serverChannel.close();
            selector.close();
            System.out.println("Server shut down.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
