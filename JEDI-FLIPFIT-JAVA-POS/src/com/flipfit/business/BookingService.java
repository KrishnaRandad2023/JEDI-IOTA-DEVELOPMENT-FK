package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IBookingService interface
 */
public class BookingService implements IBookingService {
    
    private List<Booking> bookings;
    
    public BookingService() {
        this.bookings = new ArrayList<>();
    }
    
    @Override
    public boolean createBooking(Booking booking) {
        // TODO: Implement method to create a booking
        System.out.println("Creating booking for user ID: " + booking.getUserId());
        bookings.add(booking);
        return true;
    }
    
    @Override
    public boolean confirmBooking(int bookingId) {
        // TODO: Implement method to confirm a booking
        System.out.println("Confirming booking with ID: " + bookingId);
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                booking.setStatus(BookingStatus.CONFIRMED);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean cancelBooking(int bookingId) {
        // TODO: Implement method to cancel a booking
        System.out.println("Canceling booking with ID: " + bookingId);
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                booking.setStatus(BookingStatus.CANCELLED);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Booking getBookingById(int bookingId) {
        // TODO: Implement method to get booking by ID
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }
    
    @Override
    public boolean updateBookingStatus(int bookingId, BookingStatus status) {
        // TODO: Implement method to update booking status
        System.out.println("Updating booking status for booking ID: " + bookingId);
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                booking.setStatus(status);
                return true;
            }
        }
        return false;
    }
}
