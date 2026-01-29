package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import java.util.List;

/**
 * Data Access Object interface for managing booking-related persistent data.
 * Handles the creation, retrieval, and cancellation of customer gym bookings.
 * 
 * @author team IOTA
 */
public interface BookingDAO {

    /**
     * Persists a new booking in the system.
     * 
     * @param booking the Booking object to be added
     * @return the auto-generated booking ID, or 0 if failed
     */
    int addBooking(Booking booking);

    /**
     * Retrieves a booking by its unique persistent ID.
     * 
     * @param bookingId the unique booking ID
     * @return the Booking object, or null if not found
     */
    Booking getBookingById(int bookingId);

    /**
     * Retrieves all bookings corresponding to a specific user.
     * 
     * @param userId the unique user ID
     * @return a list of all bookings for that user
     */
    List<Booking> getBookingsByUser(int userId);

    /**
     * Retrieves all bookings (across all users) for a specific time slot.
     * 
     * @param slotId the unique slot ID
     * @return a list of bookings for that slot
     */
    List<Booking> getBookingsBySlot(int slotId);

    /**
     * Updates the status of an existing booking (e.g., CONFIRMED to CANCELLED).
     * 
     * @param bookingId the unique booking ID
     * @param status    the new {@link BookingStatus}
     * @return true if the status was successfully updated
     */
    boolean updateBookingStatus(int bookingId, BookingStatus status);

    /**
     * Deletes a booking record from the system.
     * 
     * @param bookingId the unique booking ID
     * @return true if deletion was successful
     */
    boolean deleteBooking(int bookingId);

    /**
     * Counts the total number of CONFIRMED bookings for a specific slot.
     * 
     * @param slotId the unique slot ID
     * @return the count of active bookings
     */
    int getBookingCountForSlot(int slotId);
}
