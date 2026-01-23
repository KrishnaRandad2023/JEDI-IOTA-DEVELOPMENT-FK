package com.flipfit.business;

import com.flipfit.bean.Booking;

/**
 * Interface for Booking Service operations
 */
public interface IBookingService {
    
    /**
     * Create a new booking
     * @param booking Booking object
     * @return boolean indicating success
     */
    boolean createBooking(Booking booking);
    
    /**
     * Confirm a booking
     * @param bookingId Booking ID to confirm
     * @return boolean indicating success
     */
    boolean confirmBooking(int bookingId);
    
    /**
     * Cancel a booking
     * @param bookingId Booking ID to cancel
     * @return boolean indicating success
     */
    boolean cancelBooking(int bookingId);
    
    /**
     * Get booking details by ID
     * @param bookingId Booking ID
     * @return Booking object
     */
    Booking getBookingById(int bookingId);
    
    /**
     * Update booking status
     * @param bookingId Booking ID
     * @param status New booking status
     * @return boolean indicating success
     */
    boolean updateBookingStatus(int bookingId, com.flipfit.bean.BookingStatus status);
}
