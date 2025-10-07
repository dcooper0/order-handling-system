package com.OrderConsumerApplication.service;

import com.OrderConsumerApplication.exception.QueueProcessingException;
import com.order.shared.order.Order;
import com.order.shared.order.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderHandler {

    private final InventoryManagement inventoryManagement;
    private final PaymentManagement paymentManagement;
    private final JmsTemplate queueTemplate;
    private final String DUMMY_DETAILS = "dummy valid details";
    private static final Logger logger = LoggerFactory.getLogger(OrderHandler.class);

    @Value("${jms.queue.processed}")
    private String processedQueue;

    @Value("${jms.queue.rejected}")
    private String rejectedQueue;

    @Autowired
    public OrderHandler(InventoryManagement inventoryManagement,
                        PaymentManagement paymentManagement,
                        JmsTemplate queueTemplate) {
        this.inventoryManagement = inventoryManagement;
        this.paymentManagement = paymentManagement;
        this.queueTemplate = queueTemplate;
    }

    @JmsListener(destination = "${jms.queue.orders}", containerFactory = "queueListenerFactory")
    public void checkProductStockAndPaymentDetails(Order order) {
        logger.info("Received order {} for {}", order.getOrderNumber(), order.getProductName());
        if (!inventoryManagement.productIsInStock(order.getProductName())){
            logger.warn("Product {} is out of stock for order {}", order.getProductName(), order.getOrderNumber());
            rejectOrder(order, OrderStatus.OUT_OF_STOCK);
            return;
        }
        if (!paymentManagement.paymentDetailsAreValid(DUMMY_DETAILS)){
            logger.warn("Invalid payment details for order {}", order.getOrderNumber());
            rejectOrder(order, OrderStatus.INVALID_PAYMENT_DETAILS);
            return;
        }
        placeOrder(order);
    }

    public void rejectOrder(Order order, OrderStatus orderStatus){
        order.setOrderstatus(orderStatus);
        logger.info("Rejecting order {} with status {}", order.getOrderNumber(), orderStatus);
        queueTemplate.convertAndSend(rejectedQueue, order);
    }

    public void placeOrder(Order order) {
        if (paymentManagement.takePaymentFromCard(DUMMY_DETAILS)){
            inventoryManagement.takeProductFromInventory(order.getProductName());
            order.setOrderstatus(OrderStatus.COMPLETED);
            logger.info("Order {} completed successfully", order.getOrderNumber());
            try {
                queueTemplate.convertAndSend(processedQueue, order);
            } catch (JmsException e){
                logger.error("Failed to send processed order {} to queue {}", order.getOrderNumber(), processedQueue, e);
                throw new QueueProcessingException("Failed to send processed order", e);
            }
        }
    }
}
