package com.lhuang.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 * 当请求错误时，打印请求的相关信息
 * Created by lhuang
 * 2018-02-15 16:00
 */
@Component
public class BadRequestLogFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(BadRequestLogFilter.class);
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (requestContext.getResponseStatusCode() != HttpStatus.SC_OK){
            logger.error("请求的方法：{}，请求的参数：{}",request.getMethod(),
                    request.getParameterMap().keySet().stream().map((key)->key+"="+request.getParameter(key)).collect(Collectors.toList()));

            logger.error("errorMsg: {}", Optional.ofNullable(requestContext.getResponseDataStream())
                    .map((stream)->{
                        String result="null";
                        try {
                            result = IOUtils.toString(stream, "UTF-8");
                            requestContext.setResponseBody(result);
                        } catch (IOException e) {
                            logger.error("打印日志出错");
                        }
                        return result;
                    }).get());
        }


        //获取响应内容的第二种方式，原理查看RibbonRoutingFilter 或者 SimpleHostRoutingFilter的run方法
        Object zuulResponse = RequestContext.getCurrentContext().get("zuulResponse");
        if (zuulResponse != null){
            RibbonHttpResponse ribbbonHttpResponse = (RibbonHttpResponse) zuulResponse;
            try {
                String body = IOUtils.toString(ribbbonHttpResponse.getBody(),"utf-8");
                System.err.println("RESPONSE:: "+body);
                RequestContext.getCurrentContext().setResponseBody(body);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }
}
