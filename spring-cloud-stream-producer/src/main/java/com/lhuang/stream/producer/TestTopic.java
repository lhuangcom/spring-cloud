package com.lhuang.stream.producer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author LHuang
 * @since 2019/4/9
 */
public interface TestTopic {
    String OUTPUT = "example-topic-output";
    String INPUT = "example-topic-input";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();
}
