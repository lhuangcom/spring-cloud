package com.lhuang.stream.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author LHuang
 * @since 2019/4/9
 */
@EnableBinding(Sink.class)
public class SinkReceiver {


    @StreamListener(Sink.INPUT)
    public void receive(String payload){
        System.out.println(payload);
     //   throw new RuntimeException("出现异常了");
    }


}
