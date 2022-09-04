package com.flash.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static void thread1(long start_time){
        ExecutorService excutor = Executors.newSingleThreadExecutor();
        for(int i=0;i<100;i++){
            int finalI = i;
            excutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                        if(finalI == 10000){
                            System.out.println(System.currentTimeMillis() - start_time);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    static void thread2(){
        for(int i=0;i<10000;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        long start_time = System.currentTimeMillis();
        thread1(start_time);
    }

}
