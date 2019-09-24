package com.lhuang.client.producer.api.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 初始化session配置
 * @author lhunag
 * date 2019/8/12
 */

@Configuration
public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {

    /**
     * 定义Spring Session的 HttpSession 集成使用HTTP的头来取代使用
     * cookie (CookieHttpSessionStrategy())传送当前session信息
     * (升级版本后报错，不需要该配置)(在spring-social jar包中)
     * @return
     */
    /*@Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }*/

}
