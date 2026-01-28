package com.flipfit.bean;

import java.util.Date;

/**
 * The Class Waitlist.
 * 
 * @author team IOTA
 * @ClassName "Waitlist"
 */
public class Waitlist {
	/** The waitlist ID. */
	private int waitlistId;

	/** The user ID. */
	private int userId;

	/** The slot ID. */
	private int slotId;

	/** The request date. */
	private Date requestDate;

	/** The priority position. */
	private int priorityPosition;

	/**
	 * Gets the waitlist ID.
	 * 
	 * @return the waitlist ID
	 */
	public int getWaitlistId() {
		return waitlistId;
	}

	/**
	 * Sets the waitlist ID.
	 * 
	 * @param waitlistId the new waitlist ID
	 */
	public void setWaitlistId(int waitlistId) {
		this.waitlistId = waitlistId;
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
	 * Gets the request date.
	 * 
	 * @return the request date
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
	 * Sets the request date.
	 * 
	 * @param requestDate the new request date
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * Gets the priority position.
	 * 
	 * @return the priority position
	 */
	public int getPriorityPosition() {
		return priorityPosition;
	}

	/**
	 * Sets the priority position.
	 * 
	 * @param priorityPosition the new priority position
	 */
	public void setPriorityPosition(int priorityPosition) {
		this.priorityPosition = priorityPosition;
	}
}
