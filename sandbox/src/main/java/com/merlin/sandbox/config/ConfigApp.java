package com.merlin.sandbox.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// NOTE：激活配置
@EnableConfigurationProperties(ConfigurationDemo1.class)
public class ConfigApp {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApp.class, args);
    }
}
