package com.lhuang.client.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@RestController
public class ConsumerController {


    @Autowired
    private HelloRemote helloRemote;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String index(@RequestParam("name") String name) {
        Authentication authentication = new UsernamePasswordAuthenticationToken("123","456");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return helloRemote.hello(name);
    }

    @RequestMapping("/session")
    public String session(HttpSession httpSession){
        return "客户端sessionid："+httpSession.getId();
    }

    @RequestMapping("/index/{name}")
    //@HystrixCommand(fallbackMethod = "")
    public String index2(@PathVariable("name") String name) {

        return restTemplate.getForEntity("http://spring-cloud-producer/hello?name="+name,String.class).getBody();
    }

    @RequestMapping("/testError")
    public String error(){
        try {

            String message = helloRemote.error();
            System.out.println(message);
        }catch (Exception e){
            System.out.println("it's find a error");
            System.out.println(e.getCause());
        }

        return "invoke is success";
    }
}
