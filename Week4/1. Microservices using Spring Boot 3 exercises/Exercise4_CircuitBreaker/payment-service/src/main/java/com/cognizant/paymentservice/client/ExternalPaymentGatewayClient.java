package com.cognizant.paymentservice.client;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ExternalPaymentGatewayClient {

    private final Random random = new Random();

    public String executePayment(Double amount) {
        // Simulate slow response or third-party failure
        if (random.nextBoolean()) {
            throw new RuntimeException("Third-party payment gateway timeout / unavailable");
        }
        return "SUCCESS: Processed payment of $" + amount;
    }
}
