package com.flipfit.dao;

import com.flipfit.bean.Payment;

/**
 * Data Access Object interface for managing payment transactions.
 * Handles persisting payment records associated with gym bookings.
 * 
 * @author team IOTA
 */
public interface PaymentDAO {
    /**
     * Adds the payment.
     *
     * @param payment the payment
     * @return true, if successful
     */
    boolean addPayment(Payment payment);

    /**
     * Gets the payment by id.
     *
     * @param paymentId the payment ID
     * @return the payment
     */
    Payment getPaymentById(int paymentId);
}
