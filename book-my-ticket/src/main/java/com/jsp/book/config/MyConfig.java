package com.jsp.book.config;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}