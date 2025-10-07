package com.OrderHandlingApplication.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${jms.listener.concurrency:5-10}")
    private String concurrency;

    @Bean
    public MessageConverter jmsJacksonConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }



    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter jmsJacksonConverter) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(jmsJacksonConverter);
        template.setPubSubDomain(false);
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory queueListenerFactory(ConnectionFactory connectionFactory,
                                                                   MessageConverter jmsJacksonConverter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jmsJacksonConverter);
        factory.setPubSubDomain(false);
        factory.setConcurrency(concurrency);
        return factory;
    }

}
