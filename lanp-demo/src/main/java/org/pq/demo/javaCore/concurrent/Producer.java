package org.pq.demo.javaCore.concurrent;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
    private final BlockingQueue queue;

    Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        while(true){
            try {
                queue.put(produce());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    Object produce(){
        System.out.println("produce...");
        return null;
    }
}
