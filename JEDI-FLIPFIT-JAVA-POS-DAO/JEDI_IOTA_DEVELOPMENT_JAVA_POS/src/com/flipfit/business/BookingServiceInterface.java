package com.flipfit.business;

import java.util.Date;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.BookingNotDoneException;

/// Classs level Comminting

/**
 * The Interface BookingServiceInterface.
 *
 * @author team IOTA
 * @ClassName "BookingServiceInterface"
 */
public interface BookingServiceInterface {

	/**
	 * Check availability.
	 *
	 * @param slotId the slot ID
	 * @param date   the date
	 * @return true, if successful
	 */
	boolean checkAvailability(int slotId, Date date);

	/**
	 * Book slot.
	 *
	 * @param userId the user ID
	 * @param slotId the slot ID
	 * @param date   the date
	 * @throws SlotNotAvailableException the slot not available exception
	 */
	void bookSlot(int userId, int slotId, Date date) throws SlotNotAvailableException;

	/**
	 * Cancel booking.
	 *
	 * @param bookingId the booking ID
	 * @throws BookingNotDoneException the booking not done exception
	 */
	void cancelBooking(int bookingId) throws BookingNotDoneException;

}
