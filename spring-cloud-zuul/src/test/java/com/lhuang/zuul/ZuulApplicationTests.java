package com.lhuang.zuul;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZuulApplicationTests {

    @LoadBalanced
    @Autowired
    private List<RestTemplate> list;

    @Test
    public void contextLoads() {
        System.out.println(list.size());
    }

}
