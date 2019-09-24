package com.lhuang.authorization.client.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lhunag
 * date 2019/7/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {


    @Autowired
    private DefaultTokenServices defaultTokenServices;

    @Test
    public void getByUsername() {

    }
}