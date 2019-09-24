package com.lhuang.authorization.sso.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 我们需要重写WebSecurityConfigurerAdapter 否则所有的路径都会受到SSO的保护，
 * 这样无论用户访问哪个页面都会被重定向到登录页面
 *
 * 访问配置了sso的未授权客户端，会自动重定向回授权中心的登录页面
 * @author lhunag
 * date 2019/7/26
 */

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated();


    }


}
