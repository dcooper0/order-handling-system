package com.OrderHandlingApplication.controller;

import com.OrderHandlingApplication.dto.OrderDTO;
import com.OrderHandlingApplication.service.OrderService;
import com.order.shared.order.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //for testing the order history is updated and has all orders for all users.
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getOrderHistory();
    }

}
