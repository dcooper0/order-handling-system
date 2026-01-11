package com.OrderHandlingApplication.controller;

import com.OrderHandlingApplication.dto.OrderDTO;
import com.OrderHandlingApplication.service.OrderService;
import com.order.shared.order.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDTO orderDTO){
        Order order = this.orderService.placeOrder(orderDTO);
        return ResponseEntity.ok(order);
    }
}
