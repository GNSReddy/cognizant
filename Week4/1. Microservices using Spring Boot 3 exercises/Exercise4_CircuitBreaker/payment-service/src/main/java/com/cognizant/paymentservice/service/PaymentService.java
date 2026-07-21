package com.cognizant.paymentservice.service;

import com.cognizant.paymentservice.client.ExternalPaymentGatewayClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private ExternalPaymentGatewayClient externalClient;

    @CircuitBreaker(name = "paymentServiceCB", fallbackMethod = "processPaymentFallback")
    public String processPayment(Double amount) {
        logger.info("Initiating payment processing for amount: {}", amount);
        return externalClient.executePayment(amount);
    }

    public String processPaymentFallback(Double amount, Throwable throwable) {
        logger.error("Fallback triggered for payment of ${}. Reason: {}", amount, throwable.getMessage());
        return "FALLBACK: Third-party payment gateway is currently unavailable. Transaction queued for retry.";
    }
}
