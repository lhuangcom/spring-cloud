package com.lhuang.client.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author LHuang
 * @since 2019/5/6
 */
public class HelloErrorDecoder implements ErrorDecoder {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public Exception decode(String s, Response response) {

        StringBuilder builder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseExceptionResponse baseExceptionResponse = null;
        try {
            InputStream inputStream = response.body().asInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            byte[] bytes = new byte[1024];
            int bytesRead = -1;
            String content = null;
            while ( (content = bufferedReader.readLine()) != null){
                builder.append(content);
            }
            baseExceptionResponse = objectMapper.readValue(builder.toString(),BaseExceptionResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("body的内容是---"+builder.toString());
        Exception exception = baseExceptionResponse.getException();



        // 这里只封装4开头的请求异常,HystrixBadRequestException不会触发熔断异常,根据状态码判断是否是熔断异常
        if (HttpStatus.SC_BAD_REQUEST <= response.status() && response.status() < HttpStatus.SC_INTERNAL_SERVER_ERROR){
            exception = new HystrixBadRequestException("request exception wrapper", exception);
            logger.error("只封装4开头的请求异常");
        }else{
            //exception = new RuntimeException("业务异常");
            exception = new HystrixBadRequestException("request exception wrapper", exception);
            logger.error(exception.getMessage(), exception);
        }
        return exception;
    }
}
