package com.lhuang.client.producer.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lhunag
 * date 2019/9/21
 */
@EnableApolloConfig
@Configuration
public class ApolloConfig {

    @Bean
    public SampleRedisConfig sampleRedisConfig(){
        return new SampleRedisConfig();
    }

    @Bean
    public SpringBootApolloRefreshConfig springBootApolloRefreshConfig(SampleRedisConfig sampleRedisConfig, RefreshScope refreshScope){
        System.out.println(sampleRedisConfig);
        System.out.println(refreshScope);
        return new SpringBootApolloRefreshConfig(sampleRedisConfig,refreshScope);
    }
}
