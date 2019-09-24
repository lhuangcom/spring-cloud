package com.lhuang.client.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LHuang
 * @since 2019/5/7
 */
public class CustomThreadLocal {
    static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    static ExecutorService pool = Executors.newFixedThreadPool(2);
    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<100;i++) {
            int j = i;
            pool.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    CustomThreadLocal.threadLocal.set("猿天地"+j);
                    new Service().call();
                }
            }));
        }
    }
}
class Service {
    public void call() {
        CustomThreadLocal.pool.execute(new Runnable() {
            @Override
            public void run() {
                new Dao().call();
            }
        });
    }
}
class Dao {
    public void call() {
        System.out.println("Dao:" + CustomThreadLocal.threadLocal.get());
    }
}
