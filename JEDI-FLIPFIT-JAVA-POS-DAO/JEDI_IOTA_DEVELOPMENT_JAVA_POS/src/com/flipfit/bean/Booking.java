package com.flipfit.bean;

import java.util.Date;

/**
 * The Class Booking.
 * 
 * @author team IOTA
 * @ClassName "Booking"
 */
public class Booking {
	/** The booking ID. */
	private int bookingId;

	/** The user ID. */
	private int userId;

	/** The slot ID. */
	private int slotId;

	/** The booking date. */
	private Date bookingDate;

	/** The status (CONFIRMED, CANCELLED, WAITLISTED). */
	private BookingStatus status;

	/**
	 * Gets the booking ID.
	 * 
	 * @return the booking ID
	 */
	public int getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the booking ID.
	 * 
	 * @param bookingId the new booking ID
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the user ID.
	 * 
	 * @return the user ID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the user ID.
	 * 
	 * @param userId the new user ID
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the slot ID.
	 * 
	 * @return the slot ID
	 */
	public int getSlotId() {
		return slotId;
	}

	/**
	 * Sets the slot ID.
	 * 
	 * @param slotId the new slot ID
	 */
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	/**
	 * Gets the booking date.
	 * 
	 * @return the booking date
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the booking date.
	 * 
	 * @param bookingDate the new booking date
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public BookingStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
}
