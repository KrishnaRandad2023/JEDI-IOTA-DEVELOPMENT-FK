package com.flipfit.bean;

/**
 * Base class representing a user in the FlipFit system.
 * Contains common properties such as contact information and system roles.
 * 
 * @author team IOTA
 */
public class User {

	/**
	 * Default constructor for User.
	 */
	public User() {
	}

	/** Unique persistent ID of the user. */
	private int userId;

	/** Full name of the user. */
	private String name;

	/** Unique email address of the user (used for login). */
	private String email;

	/** Hashed password of the user. */
	private String password;

	/** Contact mobile number of the user. */
	private String mobileNumber;

	/** System role assigned to the user (Admin, Customer, or Owner). */
	private Role role;

	/**
	 * Gets the unique ID of the user.
	 * 
	 * @return the persistent user ID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the unique ID of the user.
	 * 
	 * @param userId the user ID to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the full name of the user.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the full name of the user.
	 * 
	 * @param name the user's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the unique email address of the user.
	 * 
	 * @return the login email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the unique email address of the user.
	 * 
	 * @param email the user's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the hashed password of the user.
	 * 
	 * @return the hashed password string
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user (should be hashed before setting).
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the user's mobile contact number.
	 * 
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Sets the user's mobile contact number.
	 * 
	 * @param mobileNumber the 10-digit mobile number
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Gets the system role of the user.
	 * 
	 * @return the Role object
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets the system role of the user (Admin, Customer, or Owner).
	 * 
	 * @param role the user's system role
	 */
	public void setRole(Role role) {
		this.role = role;
	}
}
