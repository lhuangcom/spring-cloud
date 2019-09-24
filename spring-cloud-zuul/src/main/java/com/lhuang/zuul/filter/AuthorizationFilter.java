package com.lhuang.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

/**
 * 授权验证过滤器
 * @author lhunag
 * date 2019/7/30
 */
public class AuthorizationFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String access_token = request.getHeader("Authorization");
        if (access_token == null || access_token == ""){
            logger.error("Authorization token is null");
            requestContext.setResponseStatusCode(401);
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody("Authorization token is null");
        }

        logger.info("Authorization token is ok");

        return null;
    }
}
