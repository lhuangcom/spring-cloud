package com.lhuang.client.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientConsumerApplicationTests {

    @Test
    public void contextLoads() throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024));
        System.out.println(threadPoolExecutor.getTaskCount());
        threadPoolExecutor.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println(threadPoolExecutor.getTaskCount());

                System.out.println("1"+Thread.currentThread().getName());
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("2"+Thread.currentThread().getName());
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("3"+Thread.currentThread().getName());
            }
        });

        TimeUnit.SECONDS.sleep(100);
    }

}
