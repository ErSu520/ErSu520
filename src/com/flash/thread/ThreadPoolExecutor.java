package com.flash.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutor {

    private BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(100);

    private BlockingQueue<Thread> threads ;

    /**
     * 核心池数量
     */
    private int corePoolSize = 4;

    /**
     * 线程池最大数量
     */
    private int maximumPoolSize = 8;

//	private volatile int runningThreadCount = 0;

    public ThreadPoolExecutor() {
        threads = new ArrayBlockingQueue<>(maximumPoolSize);
    }

    public ThreadPoolExecutor(int corePoolSize) {
        this();
        this.corePoolSize = corePoolSize;
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        this(corePoolSize);
        threads = new ArrayBlockingQueue<>(maximumPoolSize);
    }

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize);
    }

    public void execute(Runnable runnable) {
        synchronized (this) {
            tasks.offer(runnable);
            if(threads.size()< corePoolSize){
                System.out.println("create");
                Thread thread = new Worker();
                thread.setName("executor "+(threads.size()+1));
                thread.start();
                threads.add(thread);
            }
            this.notify();
        }
    }


    public class Worker extends Thread{
        public void run() {
            while(true) {
                Runnable runnable;
                synchronized (ThreadPoolExecutor.this) {
//					runningThreadCount--;
                    //自旋锁
                    while(tasks.size()<1) {
//                        ThreadPoolExecutor.this.notify();
                        System.out.println(Thread.currentThread().getName()+"  wake");
                        try {
                            ThreadPoolExecutor.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    runnable = tasks.poll();
//					runningThreadCount++;
                }
                if(runnable==null) {
                    continue;
                }
                runnable.run();
            }
        }
    }

}
