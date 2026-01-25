package com.flipfit.bean;

import java.util.Date;

public class Registration {
    private int registrationId;
    private String name;
    private String email;
    private String password;
    private String mobileNumber;
    private String roleType;              // "CUSTOMER" or "GYM_OWNER"
    private Date registrationDate;
    private boolean isApproved;
    private String city;                  // For gym owners - which city they operate in
    private String panNumber;             // For gym owners - PAN card for verification
    
    // Constructors
    public Registration() {
        this.registrationDate = new Date();
        this.isApproved = false;
    }
    
    public Registration(int registrationId, String name, String email, String password, 
                       String mobileNumber, String roleType) {
        this.registrationId = registrationId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.roleType = roleType;
        this.registrationDate = new Date();
        this.isApproved = false;
    }
    
    // Getters and Setters
    public int getRegistrationId() {
        return registrationId;
    }
    
    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
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
    
    public String getRoleType() {
        return roleType;
    }
    
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public boolean isApproved() {
        return isApproved;
    }
    
    public void setApproved(boolean approved) {
        isApproved = approved;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getPanNumber() {
        return panNumber;
    }
    
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
    
    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", roleType='" + roleType + '\'' +
                ", registrationDate=" + registrationDate +
                ", isApproved=" + isApproved +
                ", city='" + city + '\'' +
                ", panNumber='" + panNumber + '\'' +
                '}';
    }
}