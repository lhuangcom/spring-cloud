package com.lhuang.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.client.ClientException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 路由熔断配置:当后端服务出现异常的时候，不将异常抛出给最外层，将服务自动进行降级，直接返回我们预设的信息
 * Zuul 目前只支持服务级别的熔断，不支持具体到某个URL进行熔断。
 * 这里触发两次是因为ribbon重试
 * @author LHuang
 * @since 2019/3/19
 */
@Component
public class ProducerFallback implements FallbackProvider {

    private Logger logger = LoggerFactory.getLogger(ProducerFallback.class);

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        System.out.println(cause);
        logger.error(cause.getLocalizedMessage());
        //标记不同的异常为不同的http状态值
        //ribbon 超时会触发ClientException异常
        if (cause instanceof HystrixTimeoutException || cause instanceof ClientException) {
            return response(HttpStatus.GATEWAY_TIMEOUT);
        } else {
            //可继续添加自定义异常类
            return response(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //处理
    private ClientHttpResponse response(final HttpStatus status) {
        return new ClientHttpResponse() {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                //可替换成相应的json串的 看业务规定了
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("statusCode","500");
                hashMap.put("message","服务暂时不可用");
                ObjectMapper objectMapper = new ObjectMapper();

                return new ByteArrayInputStream(objectMapper.writeValueAsString(hashMap).getBytes("UTF-8"));
        }


    };
}
}
