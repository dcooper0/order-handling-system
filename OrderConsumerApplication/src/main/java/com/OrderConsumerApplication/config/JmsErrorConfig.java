package com.OrderConsumerApplication.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class JmsErrorConfig {

    private static final Logger logger = LoggerFactory.getLogger(JmsErrorConfig.class);

    @Bean
    public ErrorHandler jmsErrorHandler() {
        return t -> logger.error("Unhandled JMS listener exception: {}", t.getMessage(), t);
    }

}
