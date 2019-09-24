package com.lhuang.client.consumer;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;


@Component
public class HelloRemoteHystrix implements HelloRemote {


    @Override
    public String hello(String name) {
       /* ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024));
        threadPoolExecutor.shutdownNow();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.incrementAndGet();*/
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        return "sorry  " + name + " is error";
    }

    @Override
    public String error() {
        return "sorry it is error";
    }
}
