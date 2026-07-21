package com.cognizant.customerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @GetMapping("/{id}")
    public Map<String, Object> getCustomer(@PathVariable Long id) {
        return Map.of("customerId", id, "name", "John Doe", "email", "john@example.com");
    }
}
