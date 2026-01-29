package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object interface for customer-specific database operations.
 * Handles gym browsing, slot booking, and booking management for gym customers.
 * 
 * @author team IOTA
 */
public interface GymCustomerDAO {

    /**
     * Retrieves a list of all approved gym centers for customers to browse.
     * 
     * @return a list of approved GymCenter objects
     */
    List<GymCenter> viewGyms();

    /**
     * Records a new slot booking for a customer.
     * 
     * @param bookingId unique ID for the booking
     * @param slotId    ID of the slot being booked
     * @param userId    ID of the customer booking the slot
     * @param date      date of the workout session
     * @return true if the booking was successfully recorded
     */
    boolean bookSlot(int bookingId, int slotId, int userId, Date date);

    /**
     * Retrieves all bookings made by a specific customer.
     * 
     * @param userId the customer's unique ID
     * @return a list of their confirmed or cancelled bookings
     */
    List<Booking> viewBookings(int userId);

    /**
     * Cancels an existing booking based on its ID.
     * 
     * @param bookingId the unique booking ID to cancel
     * @return true if the cancellation was successful
     */
    boolean cancelBooking(int bookingId);
}
