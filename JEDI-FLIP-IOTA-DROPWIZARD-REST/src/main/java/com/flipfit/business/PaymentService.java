package com.flipfit.business;

import java.util.UUID;
import com.flipfit.bean.Payment;
import com.flipfit.dao.PaymentDAO;
import com.flipfit.dao.PaymentDAOImpl;

/**
 * The Class PaymentService.
 * 
 * @author team IOTA
 */
public class PaymentService implements PaymentServiceInterface {
    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    /**
     * Default constructor for PaymentService.
     * Initializes the default DAO implementation.
     */
    public PaymentService() {
        System.out.println("✅ PaymentService initialized with Database DAO");
    }

    /**
     * Sets a custom PaymentDAO implementation for dependency injection.
     * 
     * @param paymentDAO the DAO to be used
     */
    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public boolean processPayment(int bookingId, double amount, String paymentMethod) {
        try {
            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setAmount(amount);

            // Generate a mock transaction ID
            String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            payment.setTransactionId(transactionId);

            boolean success = paymentDAO.addPayment(payment);

            if (success) {
                System.out.println("✅ Payment processed successfully!");
                System.out.println("   Payment ID: " + payment.getPaymentId());
                System.out.println("   Transaction ID: " + payment.getTransactionId());
                System.out.println("   Amount: " + amount);
                System.out.println("   Method: " + paymentMethod);
                return true;
            } else {
                System.out.println("❌ Payment failed in Database!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Payment processing failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Payment getPaymentDetails(int paymentId) {
        return paymentDAO.getPaymentById(paymentId);
    }
}
