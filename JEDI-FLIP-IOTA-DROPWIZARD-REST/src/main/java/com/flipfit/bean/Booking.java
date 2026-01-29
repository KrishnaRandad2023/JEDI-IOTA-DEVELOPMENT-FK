package com.flipfit.bean;

import java.util.Date;

/**
 * Represents a session booking made by a customer for a specific slot.
 * Includes tracking of user ID, slot ID, booking date, and current status.
 * 
 * @author team IOTA
 */
public class Booking {

	/**
	 * Default constructor for Booking.
	 */
	public Booking() {
	}

	/** Unique persistent ID of the booking. */
	private int bookingId;

	/** ID of the user who made the booking. */
	private int userId;

	/** ID of the gym slot booked. */
	private int slotId;

	/** The date for which the booking is made. */
	private Date bookingDate;

	/** Current status of the booking (e.g., CONFIRMED, CANCELLED, COMPLETED). */
	private BookingStatus status;

	/**
	 * Gets the unique ID of the booking.
	 * 
	 * @return the persistent booking ID
	 */
	public int getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the unique ID of the booking.
	 * 
	 * @param bookingId the persistent booking ID
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the ID of the user who made the booking.
	 * 
	 * @return the customer user ID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the ID of the user who made the booking.
	 * 
	 * @param userId the customer user ID
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the ID of the booked gym slot.
	 * 
	 * @return the slot ID
	 */
	public int getSlotId() {
		return slotId;
	}

	/**
	 * Sets the ID of the booked gym slot.
	 * 
	 * @param slotId the slot ID
	 */
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	/**
	 * Gets the date on which the booking is scheduled.
	 * 
	 * @return the booking date
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the date for which the booking is scheduled.
	 * 
	 * @param bookingDate the slot date
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Gets the current life-cycle status of the booking.
	 * 
	 * @return the BookingStatus enum value
	 */
	public BookingStatus getStatus() {
		return status;
	}

	/**
	 * Sets the current status of the booking.
	 * 
	 * @param status the BookingStatus (CONFIRMED, CANCELLED, etc.)
	 */
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
}
