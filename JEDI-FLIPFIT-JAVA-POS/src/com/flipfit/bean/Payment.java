package com.flipfit.bean;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Payment class representing payment details for a booking
 */
public class Payment {
    private int paymentId;
    private int bookingId;
    private double amount;
    private PaymentStatus paymentStatus;
    private LocalDate date;
    private LocalTime time;
    private String transactionId;

    // Constructors
    public Payment() {
    }

    public Payment(int paymentId, int bookingId, double amount, PaymentStatus paymentStatus,
                   LocalDate date, LocalTime time, String transactionId) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.date = date;
        this.time = time;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Methods
    public void processPayment() {
        // Method implementation
    }

    public void refundPayment() {
        // Method implementation
    }
}
