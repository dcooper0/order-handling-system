package com.OrderConsumerApplication.config;


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
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(converter);
        template.setPubSubDomain(false); // false = queue
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory queueListenerFactory(ConnectionFactory connectionFactory,
                                                                   MessageConverter converter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        factory.setConcurrency(concurrency);
        factory.setPubSubDomain(false);
        return factory;
    }
}