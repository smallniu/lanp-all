package org.pq.demo.javaCore.concurrent;

import java.util.concurrent.BlockingQueue;

class Consumer implements Runnable {
    private final BlockingQueue queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    void consume(Object x) {
        System.out.println("consume...");
    }
}
