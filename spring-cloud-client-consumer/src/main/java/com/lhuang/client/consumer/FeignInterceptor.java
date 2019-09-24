package com.lhuang.client.consumer;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lhunag
 * date 2019/8/21
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        System.out.println("安全上下文--"+SecurityContextHolder.getContext().getAuthentication());


        ///由于线程池隔离无法通过Request获取参数
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        System.out.println("获取的参数是：----"+request.getParameter("name"));
        requestTemplate.header("name"+"qingqiutou");
    }
}
