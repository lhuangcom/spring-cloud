package com.lhuang.stream.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({TestTopic.class})
public class StreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamProducerApplication.class, args);
    }

}
