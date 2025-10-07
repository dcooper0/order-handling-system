package com.OrderHandlingApplication.messaging;

import com.order.shared.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class PaymentInventorySystemListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(PaymentInventorySystemListener.class);

    public PaymentInventorySystemListener(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @JmsListener(destination = "${jms.queue.processed}", containerFactory = "queueListenerFactory")
    public void receiveProcessedOrder(Order order){
        logger.info("ORDER SUCCESSFUL:\n" + order);
        simpMessagingTemplate.convertAndSend("/topic/orders", order);
    }

    @JmsListener(destination = "${jms.queue.rejected}", containerFactory = "queueListenerFactory")
    public void receiveRejectedOrder(Order order){
        logger.info(("ORDER UNSUCCESSFUL:\n" + order));
        simpMessagingTemplate.convertAndSend("/topic/orders", order);
        switch (order.getOrderstatus()) {
            case PAYMENT_FAILED ->
                    logger.error("Payment failed for order " + order.getOrderNumber());
            case INVALID_PAYMENT_DETAILS ->
                    logger.error("Invalid payment details for order " + order.getOrderNumber());
            case OUT_OF_STOCK ->
                    logger.error("Product " + order.getProductName() +  " out of stock for order " + order.getOrderNumber());
            default ->
                    logger.warn("Unknown issue with order " + order.getOrderNumber());
        }
    }
}
