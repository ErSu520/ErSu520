package com.flash.pool;

import com.flash.thread.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ThreadPoolExecutor executor
                = new ThreadPoolExecutor(4, 6, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        for(int i=0;i<60;i++) {
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(66);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"  "+index);
                }
            });
        }
    }

}
