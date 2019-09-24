package com.lhuang.client.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-cloud-producer", fallback = HelloRemoteHystrix.class)
public interface HelloRemote {

    @RequestMapping("/producer/hello")
    public String hello(@RequestParam(value = "name") String name);


    @RequestMapping("/producer/testError")
    public String error();

}
