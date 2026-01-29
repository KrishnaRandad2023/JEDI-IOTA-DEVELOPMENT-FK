package com.flipfit.bean;

/**
 * Represents a payment transaction for a booked slot.
 * Stores details about the amount, associated booking, and gateway transaction
 * ID.
 * 
 * @author team IOTA
 */
public class Payment {

	/**
	 * Default constructor for Payment.
	 */
	public Payment() {
	}

	/** Unique persistent ID of the payment record. */
	private int paymentId;

	/** ID of the booking associated with this payment. */
	private int bookingId;

	/** Total monetary amount paid. */
	private double amount;

	/** Unique transaction ID received from the payment gateway. */
	private String transactionId;

	/**
	 * Gets the unique ID of the payment.
	 * 
	 * @return the paymentId
	 */
	public int getPaymentId() {
		return paymentId;
	}

	/**
	 * Sets the unique ID of the payment.
	 * 
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * Gets the associated booking ID.
	 * 
	 * @return the bookingId
	 */
	public int getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the associated booking ID.
	 * 
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the payment amount.
	 * 
	 * @return the transaction amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the payment amount.
	 * 
	 * @param amount the transaction amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the gateway transaction ID.
	 * 
	 * @return the unique transaction string
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets the gateway transaction ID.
	 * 
	 * @param transactionId the unique transaction string to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
