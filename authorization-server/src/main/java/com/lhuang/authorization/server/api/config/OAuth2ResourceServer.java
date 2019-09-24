package com.lhuang.authorization.server.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 资源服务器配置
 * @author lhunag
 * date 2019/7/28
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Value(value = "${oauth2.resource.id}")
    private String oauth2ResourceId;


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 切换redis 和 jwt 的方法
     * 资源服务器认证的配置：
     * 1、设置资源服务器的标识，从配置文件中读取自定义资源名称
     * 2、设置Access Token的数据源(默认内存中)，本项目使用 redis，所以需要配置
     * @param resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //redisToken方式
        resources.resourceId(oauth2ResourceId).tokenStore(tokenStore());

        //jwt方式
        //resources.resourceId(oauth2ResourceId).tokenServices(tokenServices());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        //配置访问权限控制，/api/** 路径,必须认证过后才可以访问
        http.authorizeRequests()
                .anyRequest().authenticated().and()
                .requestMatchers().antMatchers("/api/**");
    }

   /* @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }*/

     /* @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("lhuang");
        return converter;
    }*/

    /**
     * resourceServerTokenServices 类的实例，用来实现令牌服务。
     * @return
     */
    /* @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        return defaultTokenServices;
    }*/

}

