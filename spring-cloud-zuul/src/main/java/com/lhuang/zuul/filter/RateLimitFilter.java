package com.lhuang.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.lhuang.zuul.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

public class RateLimitFilter extends ZuulFilter {


    private final RateLimiter rateLimiter = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        if (!rateLimiter.tryAcquire()){
            throw new RateLimitException();
        }
        return null;
    }
}
