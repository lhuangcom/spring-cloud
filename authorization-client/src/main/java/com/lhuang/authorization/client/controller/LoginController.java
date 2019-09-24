package com.lhuang.authorization.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lhunag
 * date 2019/7/27
 */
@RestController
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String defaultPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/callback")
    public String callback(HttpServletRequest request) {
        return request.getParameter("code");
    }

    @RequestMapping("/validerror")
    public String error() {
        System.out.println("认证失败");
        return "认证失败";
    }

    @PreAuthorize("")
    @RequestMapping("/api/info")
    public String getInfo(HttpServletResponse response) throws IOException {

        response.sendRedirect("http://localhost:8050/sso/api/userinfo");

        return "受保护的资源";
    }






}