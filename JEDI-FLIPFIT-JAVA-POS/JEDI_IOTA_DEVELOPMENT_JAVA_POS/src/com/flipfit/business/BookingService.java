package com.flipfit.business;

import java.util.Date;
import java.util.List;

import com.flipfit.bean.Booking;

public class BookingService implements BookingServiceInterface{
	@Override
    public boolean checkAvailability(int slotId, Date date) {
        // TODO: Check if slot has available seats
        return false;
    }
    
    @Override
    public void bookSlot(int userId, int slotId, Date date) {
        // TODO: Book a slot for user
        // TODO: Decrease available seats
        // TODO: If full, add to waitlist
    }
    
    
    public void cancelBooking(int bookingId) {
        // TODO: Cancel booking
        // TODO: Increase available seats
        // TODO: Promote from waitlist if applicable
    }
    
    // Additional booking methods
    public List<Booking> getBookingsByUser(int userId) {
        // TODO: Get all bookings for a user
        return null;
    }
    
    public List<Booking> getBookingsBySlot(int slotId, Date date) {
        // TODO: Get all bookings for a slot on a date
        return null;
    }
    
    public Booking getBookingById(int bookingId) {
        // TODO: Get booking details
        return null;
    }
}
