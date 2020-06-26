package com.merlin.sandbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ConfigurationDemo2 {
    @Value("${common.appKey}")
    private String appKey;
    @Value("${common.appSecret}")
    private String appSecret;
    @Value("${common.bucket}")
    private String bucket;
    @Value("${common.endPoint}")
    private String endPoint;

    @Bean
    public ConfigurationDemo2 configurationDemo2() {
        return new ConfigurationDemo2();
    }
}
