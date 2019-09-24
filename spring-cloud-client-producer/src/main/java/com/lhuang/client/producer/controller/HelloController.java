package com.lhuang.client.producer.controller;

import com.lhuang.client.producer.config.SampleRedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/producer")
public class HelloController {
    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private SampleRedisConfig sampleRedisConfig;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/hello")
    public String index(@RequestParam String name, HttpServletRequest request) throws MissingServletRequestParameterException {

       /// TimeUnit.SECONDS.sleep(3);
        logger.info(request.getPathInfo());
        logger.info("request two name is "+name);
        if (name.equals("0")){
            throw new RuntimeException("生产者出现错误了");
        }
        else if(name.equals("1")){
            throw new MissingServletRequestParameterException(name,request.getMethod());
        }

        return "hello "+name+"，this is port:"+port;
    }


    @RequestMapping("/session")
    public String session(HttpSession httpSession){
        return httpSession.getId()+ ":" + port;
    }

    @RequestMapping("/testError")
    public String error(){
        throw new RuntimeException("service invoke error");
    }

    @RequestMapping("/apollo")
    public String apollo(){

        logger.info(sampleRedisConfig.toString());

        return "apollo";
    }



}
