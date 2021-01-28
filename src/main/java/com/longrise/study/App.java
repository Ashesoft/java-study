package com.longrise.study;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 *
 */
public class App {
    public static void main(String[] args) {
        // ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
        // queue.add(new Object());
        // Object object = new Object();
        // int loops = 0;
        // Runtime runtime = Runtime.getRuntime();
        // long last = System.currentTimeMillis();
        // while (true) {
        //     if(loops % 10000 == 0){
        //         long now = System.currentTimeMillis();
        //         long duration = now - last;
        //         last = now;
        //         System.out.println(String.format("duration=%d queue.size=%d memory max=%d free=%d total=%d%n", duration, queue.size(), runtime.maxMemory(), runtime.freeMemory(), runtime.totalMemory()));
        //     }
        //     queue.add(object);
        //     queue.remove(object);
        //     ++loops;
        // }
        ss();
    }

    public static void ss(){
        for (int i = 1; i <= 108; i++) {
            
            String ss = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(ss);
        }
    }
}
