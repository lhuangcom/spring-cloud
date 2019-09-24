package com.lhuang.authorization.client.api.config;

import com.lhuang.authorization.client.api.filter.Oauth2Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器注册
 * @author lhunag
 * date 2019/7/29
 */
@Configuration
public class InterceptorRegisterConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Oauth2Interceptor())
                .addPathPatterns("/api/userInfo");
    }
}
