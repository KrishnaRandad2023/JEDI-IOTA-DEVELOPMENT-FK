package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Booking;
import java.util.List;

/**
 * Interface for Gym Customer Service operations
 */
public interface IGymCustomerService {
    
    /**
     * View gym centers by area/city
     * @param area Area or city name
     * @return List of gym centers in the specified area
     */
    List<GymCenter> viewCenterByArea(String area);
    
    /**
     * Check availability at a gym center
     * @param centerId Center ID
     * @param date Date for which to check availability
     * @return List of available slots
     */
    List<com.flipfit.bean.Slot> checkAvailability(int centerId, java.time.LocalDate date);
    
    /**
     * Make a booking
     * @param booking Booking object with booking details
     * @return boolean indicating success
     */
    boolean makeBooking(Booking booking);
    
    /**
     * Cancel a booking
     * @param bookingId Booking ID to cancel
     * @return boolean indicating success
     */
    boolean cancelBooking(int bookingId);
    
    /**
     * View all bookings for a customer
     * @param userId User ID
     * @return List of bookings
     */
    List<Booking> viewMyBookings(int userId);
}
