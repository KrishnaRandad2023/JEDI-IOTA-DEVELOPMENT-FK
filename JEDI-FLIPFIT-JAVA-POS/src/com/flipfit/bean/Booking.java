package com.flipfit.bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Booking class representing a gym booking
 */
public class Booking {
    private int bookingId;
    private int userId;
    private int centerId;
    private int slotId;
    private LocalDateTime bookingTime;
    private BookingStatus status;
    private LocalDate date;
    private LocalTime endTime;
    private LocalTime startTime;
    private LocalDateTime verifiedTime;
    private Payment payment;

    // Constructors
    public Booking() {
    }

    public Booking(int bookingId, int userId, int centerId, int slotId, LocalDateTime bookingTime,
                   BookingStatus status, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.centerId = centerId;
        this.slotId = slotId;
        this.bookingTime = bookingTime;
        this.status = status;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getVerifiedTime() {
        return verifiedTime;
    }

    public void setVerifiedTime(LocalDateTime verifiedTime) {
        this.verifiedTime = verifiedTime;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    // Methods
    public void confirmBooking() {
        // Method implementation
    }

    public void cancelBooking() {
        // Method implementation
    }
}
