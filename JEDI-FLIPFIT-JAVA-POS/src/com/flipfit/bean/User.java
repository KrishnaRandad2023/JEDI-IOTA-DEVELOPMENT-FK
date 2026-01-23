package com.flipfit.bean;

/**
 * Base User class representing common attributes for all users
 */
public class User {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;

    // Constructors
    public User() {
    }

    public User(int userId, String userName, String email, String password, String phoneNumber, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Methods
    public void login(String email, String password) {
        // Method implementation
    }

    public void register(User user) {
        // Method implementation
    }
}
