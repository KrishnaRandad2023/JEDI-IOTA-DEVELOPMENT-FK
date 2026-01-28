package com.flipfit.bean;

/**
 * The Class User.
 * 
 * @author team IOTA
 * @ClassName "User"
 */
public class User {
	/** The user ID. */
	private int userId;

	/** The name. */
	private String name;

	/** The email. */
	private String email;

	/** The password. */
	private String password;

	/** The mobile number. */
	private String mobileNumber;

	/** The role. */
	private Role role;

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
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the mobile number.
	 * 
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Sets the mobile number.
	 * 
	 * @param mobileNumber the new mobile number
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 * 
	 * @param role the new role
	 */
	public void setRole(Role role) {
		this.role = role;
	}
}
