package com.flipfit.business;

import com.flipfit.bean.Payment;

/**
 * Interface for Payment Operations
 * 
 * @author team IOTA
 */
public interface PaymentServiceInterface {
    /**
     * Process a payment for a booking
     * 
     * @param bookingId     The ID of the booking
     * @param amount        The amount to be paid
     * @param paymentMethod The method of payment (Card, UPI, etc.)
     * @return true if payment is successful, false otherwise
     */
    boolean processPayment(int bookingId, double amount, String paymentMethod);

    /**
     * Get payment details by payment ID
     * 
     * @param paymentId The ID of the payment
     * @return Payment object if found, null otherwise
     */
    Payment getPaymentDetails(int paymentId);
}
