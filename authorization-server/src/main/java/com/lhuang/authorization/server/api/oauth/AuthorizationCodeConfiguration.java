package com.lhuang.authorization.server.api.oauth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Base64;

/**
 * @author lhunag
 * date 2019/7/28
 */
@Component
public class AuthorizationCodeConfiguration {

    /**
     * oauth2 会对请求头中（Authorization: Basic clientId:secret）
     * 进行Base64 decode处理，故需要编码处理；
     * @param username
     * @param password
     * @return
     */
    public String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        String encoded = new String(Base64.getEncoder().encode(
                credentials.getBytes()));

        return encoded;
    }

    public MultiValueMap<String, String> getBody(String authorizationCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("scope", "all");
        formData.add("code", authorizationCode);
        formData.add("redirect_uri", "http://localhost:8060/callback");
        return formData;
    }

    public HttpHeaders getHeader(String clientAuthentication) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Basic " + clientAuthentication);

        return httpHeaders;
    }

}
