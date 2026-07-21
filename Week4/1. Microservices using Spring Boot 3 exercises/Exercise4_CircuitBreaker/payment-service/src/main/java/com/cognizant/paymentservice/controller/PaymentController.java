package com.cognizant.paymentservice.controller;

import com.cognizant.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public String processPayment(@RequestParam Double amount) {
        return paymentService.processPayment(amount);
    }
}
