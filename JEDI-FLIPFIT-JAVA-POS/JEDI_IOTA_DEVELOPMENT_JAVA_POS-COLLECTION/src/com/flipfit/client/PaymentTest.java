package com.flipfit.client;

import com.flipfit.business.PaymentService;

public class PaymentTest {
    public static void main(String[] args) {
        System.out.println("Starting Payment Service Verification...");

        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            PaymentService paymentService = factory.getPaymentService();

            if (paymentService == null) {
                System.err.println("❌ FAILED: PaymentService is null from factory!");
                System.exit(1);
            }
            System.out.println("✅ PaymentService retrieved from factory.");

            boolean success = paymentService.processPayment(101, 500.0, "Card");

            if (success) {
                System.out.println("✅ Payment processed successfully.");
            } else {
                System.err.println("❌ FAILED: Payment processing returned false.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
