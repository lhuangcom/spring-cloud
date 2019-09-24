package com.lhuang.authorization.sso.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author lhunag
 * date 2019/7/26
 */
@RestController
public class UserController {



    @RequestMapping("/api/userinfo")
    public String getUserInfo(Authentication authentication, Principal principal) {
        System.out.println("成功访问");
        System.out.println(SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        System.out.println(authentication);
        System.out.println(principal);
        return "重定向保护的资源";
    }

    @RequestMapping("/login")
    public String login() {
        return "成功重定向";
    }

    @RequestMapping("/callback")
    public String callback() {
        System.out.println("成功回调");
        return "成功回调";
    }

}
