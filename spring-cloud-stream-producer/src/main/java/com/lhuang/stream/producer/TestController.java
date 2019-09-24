package com.lhuang.stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author LHuang
 * @since 2019/4/9
 */
@RestController
public class TestController {

    @Autowired
    private TestTopic testTopic;


    @GetMapping("/sendMessage")
    public String messageWithMQ(@RequestParam String message) {
        System.out.println("打印testTopic--"+testTopic);
        System.out.println("打印testTopic--"+testTopic.output());
        testTopic.output().send(MessageBuilder.withPayload(message).build());
        System.out.println("打印testTopic--"+testTopic);
        return "ok";
    }

}
