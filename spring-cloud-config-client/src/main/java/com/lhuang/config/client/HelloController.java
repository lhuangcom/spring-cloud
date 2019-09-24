package com.lhuang.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LHuang
 * @since 2019/3/18
 */

@RestController
@RefreshScope   //用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class HelloController {

    @Value("${foo}")
    String foo;

    @RequestMapping("/hello")
    public String hello(){
        return foo;
    }
}
