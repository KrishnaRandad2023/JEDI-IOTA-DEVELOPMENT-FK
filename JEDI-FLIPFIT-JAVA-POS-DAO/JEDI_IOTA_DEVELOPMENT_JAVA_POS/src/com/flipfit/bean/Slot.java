package com.flipfit.bean;

import java.time.LocalTime;

/**
 * The Class Slot.
 * 
 * @author team IOTA
 * @ClassName "Slot"
 */
public class Slot {
	/** The slot ID. */
	private int slotId;

	/** The center ID. */
	private int centerId;

	/** The start time. */
	private LocalTime startTime;

	/** The end time. */
	private LocalTime endTime;

	/** The total seats. */
	private int totalSeats;

	/** The available seats. */
	private int availableSeats;

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
	 * Gets the center ID.
	 * 
	 * @return the center ID
	 */
	public int getCenterId() {
		return centerId;
	}

	/**
	 * Sets the center ID.
	 * 
	 * @param centerId the new center ID
	 */
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	/**
	 * Gets the start time.
	 * 
	 * @return the start time
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 * 
	 * @param startTime the new start time
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 * 
	 * @return the end time
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 * 
	 * @param endTime the new end time
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the total seats.
	 * 
	 * @return the total seats
	 */
	public int getTotalSeats() {
		return totalSeats;
	}

	/**
	 * Sets the total seats.
	 * 
	 * @param totalSeats the new total seats
	 */
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	/**
	 * Gets the available seats.
	 * 
	 * @return the available seats
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * Sets the available seats.
	 * 
	 * @param availableSeats the new available seats
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
}
