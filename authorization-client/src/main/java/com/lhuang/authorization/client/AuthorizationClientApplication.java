package com.lhuang.authorization.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 授权服务器和认证中心
 */
@SpringBootApplication
public class AuthorizationClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationClientApplication.class, args);
    }

}
