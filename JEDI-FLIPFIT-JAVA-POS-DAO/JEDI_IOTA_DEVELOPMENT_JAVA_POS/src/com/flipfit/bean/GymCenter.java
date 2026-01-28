package com.flipfit.bean;

/**
 * The Class GymCenter.
 * 
 * @author team IOTA
 * @ClassName "GymCenter"
 */
public class GymCenter {
	/** The center ID. */
	private int centerId;

	/** The owner ID. */
	private int ownerId;

	/** The center name. */
	private String centerName;

	/** The address. */
	private String address;

	/** The city. */
	private String city;

	/** The capacity. */
	private int capacity;

	/** The approval status. */
	private boolean isApproved;

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
	 * Gets the owner ID.
	 * 
	 * @return the owner ID
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * Sets the owner ID.
	 * 
	 * @param ownerId the new owner ID
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * Gets the center name.
	 * 
	 * @return the center name
	 */
	public String getCenterName() {
		return centerName;
	}

	/**
	 * Sets the center name.
	 * 
	 * @param centerName the new center name
	 */
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	/**
	 * Gets the address.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * 
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the city.
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 * 
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the capacity.
	 * 
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the capacity.
	 * 
	 * @param capacity the new capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Checks if is approved.
	 * 
	 * @return true, if is approved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * Sets the approved status.
	 * 
	 * @param isApproved the new approved status
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
}
