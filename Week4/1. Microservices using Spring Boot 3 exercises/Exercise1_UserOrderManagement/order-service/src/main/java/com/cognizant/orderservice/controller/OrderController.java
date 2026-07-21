package com.cognizant.orderservice.controller;

import com.cognizant.orderservice.client.UserClient;
import com.cognizant.orderservice.dto.OrderResponseDTO;
import com.cognizant.orderservice.dto.UserDTO;
import com.cognizant.orderservice.entity.Order;
import com.cognizant.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserClient userClient;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    UserDTO user = null;
                    try {
                        user = userClient.getUserById(order.getUserId());
                    } catch (Exception e) {
                        // user service down or user not found
                    }
                    return ResponseEntity.ok(new OrderResponseDTO(order, user));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
