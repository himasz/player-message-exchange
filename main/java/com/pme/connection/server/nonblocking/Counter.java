package com.pme.connection.server.nonblocking;

public class Counter {
    int count = 1;

    public void increment() {
        count++;
    }

    public int get() {
        return count;
    }
}

