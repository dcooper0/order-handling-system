package com.OrderHandlingApplication.service;

import com.OrderHandlingApplication.dto.OrderDTO;
import com.OrderHandlingApplication.exception.CustomerNotFoundException;
import com.OrderHandlingApplication.exception.InvalidOrderException;
import com.OrderHandlingApplication.messaging.OrderProducer;
import com.order.shared.order.Order;
import com.order.shared.order.OrderStatus;
import com.order.shared.user.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class OrderService {

    private final RestClient restClient;
    private final List<Order> orderHistory;
    private final AtomicInteger orderNumber = new AtomicInteger();
    private final OrderProducer orderProducer;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(RestClient restClient, OrderProducer orderProducer) {
        this.restClient = restClient;
        this.orderHistory = new ArrayList<>();
        this.orderProducer = orderProducer;
    }

    public Order placeOrder(OrderDTO orderDTO) {
        if (orderDTO == null
                || orderDTO.getCustomerName() == null
                || orderDTO.getProductName() == null){
            throw new InvalidOrderException("Customer name and product name are required");
        }
        logger.info("Placing new order for customer '{}' - product '{}'", orderDTO.getCustomerName(), orderDTO.getProductName());
        Order order = createOrderWithCustomerDetailsFromApi(orderDTO);
        orderProducer.sendOrderForPaymentProcessingAndInventoryCheck(order);
        this.orderHistory.add(order);
        return order;
    }

    public Order createOrderWithCustomerDetailsFromApi(OrderDTO orderDTO) {
        Customer[] customersMatchingName = restClient.get().
                uri("/users?name=" + orderDTO.getCustomerName()).
                retrieve().
                body(Customer[].class);

        if (customersMatchingName.length == 0 || customersMatchingName == null){
            throw new CustomerNotFoundException(orderDTO.getCustomerName());
        }

        Customer customer = customersMatchingName[0];
        return new Order(customer.getName(),
                orderDTO.getProductName(),
                customer.getAddress(),
                createOrderReference(),
                OrderStatus.PLACED);
    }

    public int createOrderReference(){
        return orderNumber.incrementAndGet();
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }
}
