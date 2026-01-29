package com.flipfit.business;

import com.flipfit.exception.BookingNotDoneException;
import java.util.Date;
import java.util.List;
import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import com.flipfit.exception.SlotNotAvailableException;

/**
 * Interface for Booking Service Operations.
 * Provides methods to manage gym slot bookings, cancellations, and status
 * updates.
 * 
 * @author team IOTA
 */
public interface BookingServiceInterface {

	/**
	 * Checks the availability of seats for a specific slot on a given date.
	 *
	 * @param slotId the ID of the slot to check
	 * @param date   the date for which availability is to be checked
	 * @return true if seats are available, false otherwise
	 */
	boolean checkAvailability(int slotId, Date date);

	/**
	 * Books a slot for a user on a specified date.
	 *
	 * @param userId the ID of the user booking the slot
	 * @param slotId the ID of the slot to be booked
	 * @param date   the date for the booking
	 * @return true if booking is successful
	 * @throws SlotNotAvailableException if the chosen slot has no available seats
	 */
	int bookSlot(int userId, int slotId, Date date) throws SlotNotAvailableException;

	/**
	 * Cancels an existing booking.
	 *
	 * @param bookingId the ID of the booking to cancel
	 * @return true if cancellation is successful
	 * @throws BookingNotDoneException if the booking cannot be found or cancelled
	 */
	boolean cancelBooking(int bookingId) throws BookingNotDoneException;

	/**
	 * Updates the status of a specific booking.
	 *
	 * @param bookingId the ID of the booking to update
	 * @param status    the new status to set for the booking
	 * @return true if the update is successful
	 * @throws BookingNotDoneException if the booking update fails
	 */
	boolean updateBookingStatus(int bookingId, BookingStatus status) throws BookingNotDoneException;

	/**
	 * Deletes a booking record from the system.
	 *
	 * @param bookingId the ID of the booking to delete
	 * @return true if deletion is successful
	 * @throws BookingNotDoneException if the deletion fails
	 */
	boolean deleteBooking(int bookingId) throws BookingNotDoneException;

	/**
	 * Retrieves the current number of confirmed bookings for a specific slot.
	 *
	 * @param slotId the ID of the slot
	 * @return the count of bookings for the slot
	 */
	int getBookingCountForSlot(int slotId);

	/**
	 * Retrieves all bookings for a specific user.
	 *
	 * @param userId the ID of the user
	 * @return a list of bookings associated with the user
	 */
	List<Booking> getBookingsByUser(int userId);

	/**
	 * Retrieves all bookings in the system.
	 *
	 * @return a list containing all booking records
	 */
	List<Booking> getAllBookings();

	/**
	 * Checks if a user has already booked a specific slot.
	 *
	 * @param userId the ID of the user
	 * @param slotId the ID of the slot
	 * @return true if a booking exists, false otherwise
	 */
	boolean hasUserBookedSlot(int userId, int slotId);
}
