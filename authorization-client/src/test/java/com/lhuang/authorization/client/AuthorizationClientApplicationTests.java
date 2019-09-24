package com.lhuang.authorization.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationClientApplicationTests {

    @Test
    public void contextLoads() {

       String password =  new BCryptPasswordEncoder().encode("123");
        String password1 =  new BCryptPasswordEncoder().encode("123");
        System.out.println(new BCryptPasswordEncoder().matches("123",password));
        System.out.println(password);
        System.out.println(password1);
    }

}
