package com.flipfit.business;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.flipfit.bean.Payment;

/**
 * The Class PaymentService.
 * 
 * @author team IOTA
 */
public class PaymentService implements PaymentServiceInterface {
    private Map<Integer, Payment> payments;
    private int paymentIdCounter;

    public PaymentService() {
        this.payments = new HashMap<>();
        this.paymentIdCounter = 1;
        System.out.println("✅ PaymentService initialized");
    }

    @Override
    public boolean processPayment(int bookingId, double amount, String paymentMethod) {
        try {
            Payment payment = new Payment();
            payment.setPaymentId(paymentIdCounter++);
            payment.setBookingId(bookingId);
            payment.setAmount(amount);

            // Generate a mock transaction ID
            String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            payment.setTransactionId(transactionId);

            // In a real system, we would integrate with a payment gateway here.
            // For this mock, we assume success.

            payments.put(payment.getPaymentId(), payment);

            System.out.println("✅ Payment processed successfully!");
            System.out.println("   Payment ID: " + payment.getPaymentId());
            System.out.println("   Transaction ID: " + payment.getTransactionId());
            System.out.println("   Amount: " + amount);
            System.out.println("   Method: " + paymentMethod);

            return true;
        } catch (Exception e) {
            System.out.println("❌ Payment processing failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Payment getPaymentDetails(int paymentId) {
        return payments.get(paymentId);
    }
}
