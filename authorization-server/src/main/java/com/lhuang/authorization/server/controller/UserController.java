package com.lhuang.authorization.server.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhunag
 * date 2019/7/26
 */
@RestController
public class UserController {



    @RequestMapping("/api/userinfo")
    public String getUserInfo() {
        System.out.println(SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        System.out.println("成功访问");
        return "重定向保护的资源";
    }

    @RequestMapping("/login")
    public String login() {

        return "成功重定向";
    }


}
