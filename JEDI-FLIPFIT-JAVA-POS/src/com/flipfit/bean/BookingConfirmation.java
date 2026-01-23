package com.flipfit.bean;

/**
 * BookingConfirmation class for booking confirmation notifications
 */
public class BookingConfirmation {
    private int bookingId;
    private String message;

    // Constructors
    public BookingConfirmation() {
    }

    public BookingConfirmation(int bookingId, String message) {
        this.bookingId = bookingId;
        this.message = message;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Methods
    public void generateConfirmation() {
        // Method implementation
    }
}
