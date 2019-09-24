package com.lhuang.stream.producer;

import org.springframework.context.annotation.Configuration;


/**
 * @author LHuang
 * @since 2019/4/9
 */
@Configuration
public class SinkSender {


   /* @Bean
    @InboundChannelAdapter(value = Source.OUTPUT,poller = @Poller(fixedDelay = "20000"))
    public MessageSource<String> trimMessageSource(){
        return () -> new GenericMessage<>("send some message");
    }*/

}
