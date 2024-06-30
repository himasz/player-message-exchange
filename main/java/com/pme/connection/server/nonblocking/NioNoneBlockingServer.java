package com.pme.connection.server.nonblocking;

import com.pme.connection.server.IServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NioNoneBlockingServer implements IServer {
    private final int port;
    private Selector selector;
    private ServerSocketChannel serverChannel;
    Executor networkExecutor = Executors.newSingleThreadExecutor();
    Executor readingEXecutor = Executors.newSingleThreadExecutor();
    private final CopyOnWriteArrayList<SocketChannel> clients = new CopyOnWriteArrayList<>();
    private volatile boolean running;

    public NioNoneBlockingServer() {
        this(3455);
    }

    public NioNoneBlockingServer(int port) {
        this.port = port;
    }

    @Override
    public void startServer() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        running = true;
        System.out.println("Server started on port " + serverChannel.socket().getLocalPort());

        networkExecutor.execute(this::connectionLoop);
    }

    private void connectionLoop() {
        while (running) {
            try {
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
                        SocketChannel channel = (SocketChannel) key.channel();
                        readingEXecutor.execute(new NonBlockingClientHandler(key, channel, clients));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        closeAll();
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
