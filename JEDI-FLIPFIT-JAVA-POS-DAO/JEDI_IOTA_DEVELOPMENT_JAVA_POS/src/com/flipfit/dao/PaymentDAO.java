package com.flipfit.dao;

import com.flipfit.bean.Payment;

/**
 * The Interface PaymentDAO.
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
