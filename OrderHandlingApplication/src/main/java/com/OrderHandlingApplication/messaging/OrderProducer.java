package com.OrderHandlingApplication.messaging;

import com.order.shared.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final JmsTemplate queueTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${jms.queue.orders}")
    private String orderQueue;

    @Autowired
    public OrderProducer(JmsTemplate queueTemplate) {
        this.queueTemplate = queueTemplate;
    }

    public void sendOrderForPaymentProcessingAndInventoryCheck(Order order){
        try {
            logger.info("Sending order to queue: " + orderQueue);
            this.queueTemplate.convertAndSend(orderQueue, order);
        } catch (JmsException e){
            logger.error("Failed to send order {} to queue {}: {}", order.getOrderNumber(), orderQueue, e.getMessage(), e);
        }

    }
}
