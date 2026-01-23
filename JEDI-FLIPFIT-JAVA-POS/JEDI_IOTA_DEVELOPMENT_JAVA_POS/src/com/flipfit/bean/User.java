package com.flipfit.bean;

public class User {
	private int userId;
    private String name;
    private String email;
    private String password;
    private String mobileNumber;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
//	public Role getRole() {
//		return role;
//	}
//	public void setRole(Role role) {
//		this.role = role;
//	}
	
    //private Role role;
}
