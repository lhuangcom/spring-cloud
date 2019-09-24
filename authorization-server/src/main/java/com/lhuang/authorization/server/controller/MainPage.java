package com.lhuang.authorization.server.controller;

import com.lhuang.authorization.server.api.model.UserInfo;
import com.lhuang.authorization.server.api.oauth.AuthorizationCodeTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;

/**
 * @author lhunag
 * date 2019/7/28
 */
@Controller
public class MainPage {
    @Autowired
    private AuthorizationCodeTokenService tokenService;


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/callback")
    public ModelAndView callback(String code, String state) {

        System.out.println("成功重定向");
      /*  OAuth2Token token = tokenService.getToken(code);

        return new ModelAndView("redirect:/mainpage");*/
      return null;
    }

    @GetMapping("/mainpage")
    public ModelAndView mainpage() {


        //判断是否有token，不存在则获取token

        //return new ModelAndView("redirect:" + authEndpoint);


        //获取受保护的资源
        //tryToGetUserInfo(mv, clientUser.getAccessToken());

        return null;
    }

    private void tryToGetUserInfo(ModelAndView mv, String token) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        String endpoint = "http://localhost:8070/api/userinfo";

        try {
            RequestEntity<Object> request = new RequestEntity<>(
                    headers, HttpMethod.GET, URI.create(endpoint));

            ResponseEntity<UserInfo> userInfo = restTemplate.exchange(request, UserInfo.class);

            if (userInfo.getStatusCode().is2xxSuccessful()) {
                mv.addObject("userInfo", userInfo.getBody());
            } else {
                throw new RuntimeException("it was not possible to retrieve user profile");
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }

}
