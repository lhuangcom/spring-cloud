package com.lhuang.client.producer.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lhunag
 * date 2019/9/22
 */
@ConditionalOnProperty("redis.cache.enabled")
@Component
public class SpringBootApolloRefreshConfig {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootApolloRefreshConfig.class);

    private final SampleRedisConfig sampleRedisConfig;
    private final RefreshScope refreshScope;

    public SpringBootApolloRefreshConfig(
            final SampleRedisConfig sampleRedisConfig,
            final RefreshScope refreshScope) {
        this.sampleRedisConfig = sampleRedisConfig;
        this.refreshScope = refreshScope;
    }

    @ApolloConfigChangeListener(value = {"application.yml"},
            interestedKeyPrefixes = {"redis.cache."})
    public void onChange(ConfigChangeEvent changeEvent) {
        logger.info("before refresh {}", sampleRedisConfig.toString());
        refreshScope.refresh("sampleRedisConfig");
        logger.info("after refresh {}", sampleRedisConfig.toString());
    }
}