package com.lhuang.client.consumer;

import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient  //启用服务注册与发现
@EnableFeignClients  //启用feign进行远程调用
@EnableHystrix       //启用熔断器
@EnableHystrixDashboard  //启用熔断器监控器
@SpringBootApplication
public class ClientConsumerApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new HelloErrorDecoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientConsumerApplication.class, args);
    }

}
