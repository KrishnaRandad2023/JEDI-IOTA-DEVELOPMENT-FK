package com.flipfit.bean;

import java.time.LocalTime;

/**
 * Represents a specific time slot available at a gym center.
 * Tracks timing, total capacity, and currently available seats.
 * 
 * @author team IOTA
 */
public class Slot {

	/**
	 * Default constructor for Slot.
	 */
	public Slot() {
	}

	/** Unique persistent ID of the time slot. */
	private int slotId;

	/** ID of the gym center where this slot is located. */
	private int centerId;

	/** The time the workout session begins. */
	private LocalTime startTime;

	/** The time the workout session ends. */
	private LocalTime endTime;

	/** Total number of seats available for this slot. */
	private int totalSeats;

	/** Currently available seats for booking. */
	private int availableSeats;

	/**
	 * Gets the unique ID of the slot.
	 * 
	 * @return the slot ID
	 */
	public int getSlotId() {
		return slotId;
	}

	/**
	 * Sets the unique ID of the slot.
	 * 
	 * @param slotId the persistent slot ID
	 */
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	/**
	 * Gets the ID of the gym center associated with this slot.
	 * 
	 * @return the center ID
	 */
	public int getCenterId() {
		return centerId;
	}

	/**
	 * Sets the ID of the gym center associated with this slot.
	 * 
	 * @param centerId the parent center ID
	 */
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	/**
	 * Gets the start time of the workout session.
	 * 
	 * @return the session start time
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time of the workout session.
	 * 
	 * @param startTime the session start time
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time of the workout session.
	 * 
	 * @return the session end time
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time of the workout session.
	 * 
	 * @param endTime the session end time
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the total seat capacity defined for this slot.
	 * 
	 * @return the total seats
	 */
	public int getTotalSeats() {
		return totalSeats;
	}

	/**
	 * Sets the total seat capacity defined for this slot.
	 * 
	 * @param totalSeats the maximum seats
	 */
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	/**
	 * Gets the number of seats currently available for new bookings.
	 * 
	 * @return the count of available seats
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * Sets the number of seats currently available for new bookings.
	 * 
	 * @param availableSeats the count of available seats
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
}
