package com.or_yaakov.couponsystem.rest.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfiguration {

    @Bean(name = "tokens")
    public Map<String, ClientSession> tokensMap() {
        return new HashMap<>();
    }
}