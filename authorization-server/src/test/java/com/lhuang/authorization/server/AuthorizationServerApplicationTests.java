package com.lhuang.authorization.server;

import com.lhuang.authorization.server.api.oauth.AuthorizationCodeConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationServerApplicationTests {

    @Autowired
    private AuthorizationCodeConfiguration authorizationCodeConfiguration;

    @Test
    public void contextLoads() {
       String encode =  authorizationCodeConfiguration.encodeCredentials("123","456");

        System.out.println(encode);
    }

}
