package com.flipfit.bean;

/**
 * Represents a gym center in the system.
 * Contains details such as location, capacity, and administrative approval
 * status.
 * 
 * @author team IOTA
 */
public class GymCenter {

	/**
	 * Default constructor for GymCenter.
	 */
	public GymCenter() {
	}

	/** Unique ID of the gym center. */
	private int centerId;

	/** ID of the user who owns this gym center. */
	private int ownerId;

	/** Name of the gym center. */
	private String centerName;

	/** Physical address of the gym. */
	private String address;

	/** City where the gym is located. */
	private String city;

	/** Total seat capacity of the gym center. */
	private int capacity;

	/** Whether the gym registration has been approved by an administrator. */
	private boolean isApproved;

	/**
	 * Gets the unique ID of the gym center.
	 * 
	 * @return the center ID
	 */
	public int getCenterId() {
		return centerId;
	}

	/**
	 * Sets the unique ID of the gym center.
	 * 
	 * @param centerId the unique center ID
	 */
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}

	/**
	 * Gets the ID of the gym owner.
	 * 
	 * @return the owner user ID
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * Sets the ID of the gym owner.
	 * 
	 * @param ownerId the owner user ID
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Gets the name of the gym center.
	 * 
	 * @return the center name
	 */
	public String getCenterName() {
		return centerName;
	}

	/**
	 * Sets the name of the gym center.
	 * 
	 * @param centerName the gym name
	 */
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	/**
	 * Gets the physical address of the gym.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the physical address of the gym.
	 * 
	 * @param address the store address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the city location of the gym.
	 * 
	 * @return the city name
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city location of the gym.
	 * 
	 * @param city the city name
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the total seat capacity of the gym center.
	 * 
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the total seat capacity of the gym center.
	 * 
	 * @param capacity the maximum capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Checks if the center is approved by an administrator.
	 * 
	 * @return true if approved, false otherwise
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * Sets the administrative approval status of the gym.
	 * 
	 * @param isApproved true to approve, false to set as pending/rejected
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
}
