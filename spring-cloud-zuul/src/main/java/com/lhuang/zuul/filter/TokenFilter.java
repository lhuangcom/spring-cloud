package com.lhuang.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 配置zuul过滤器
 * @author LHuang
 * @since 2019/3/19
 */
public class TokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public String filterType() {
        return PRE_TYPE;
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
    public Object run() {
        System.out.println("执行过滤器");
        return null;

       /* RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());

        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
          //对请求是否路由
          ctx.setSendZuulResponse(true);
          ctx.set("isSuccess",true);
          ctx.setResponseStatusCode(200);
        }else {
            ctx.setSendZuulResponse(false);
            ctx.set("isSuccess",false);
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
        }
        return null;*/
    }
}
