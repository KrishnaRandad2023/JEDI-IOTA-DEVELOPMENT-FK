package com.flipfit.bean;

import java.util.Date;

/**
 * Represents a user registration request in the FlipFit system.
 * Used for storing details of both gym customers and gym owners during
 * the initial registration or approval process.
 * 
 * @author team IOTA
 */
public class Registration {

    /** Unique persistent ID of the registration request. */
    private int registrationId;

    /** Full name of the registrant. */
    private String name;

    /** Email address for the registration (used as primary key/login). */
    private String email;

    /**
     * Raw password provided during registration (should be hashed before persisting
     * to User table).
     */
    private String password;

    /** 10-digit mobile contact number. */
    private String mobileNumber;

    /** The type of role requested (e.g., "CUSTOMER" or "GYM_OWNER"). */
    private String roleType;

    /** The timestamp when the registration was initiated. */
    private Date registrationDate;

    /** Whether the registration request has been approved by an administrator. */
    private boolean isApproved;

    /** City location of the registrant or their gym (primarily for gym owners). */
    private String city;

    /** PAN card number (required for gym owners). */
    private String panNumber;

    /** GST registration number (required for gym owners). */
    private String gstNumber;

    /** Corporate Identification Number (if applicable, for gym owners). */
    private String cin;

    /** 12-digit Aadhaar number for identity verification. */
    private String aadhaarNumber;

    /**
     * Default constructor. Initializes the registration date to current time
     * and sets approval status to false.
     */
    public Registration() {
        this.registrationDate = new Date();
        this.isApproved = false;
    }

    /**
     * Parameterized constructor for creating a registration request with basic
     * details.
     *
     * @param registrationId unique ID
     * @param name           full name
     * @param email          contact email
     * @param password       raw password
     * @param mobileNumber   10-digit mobile
     * @param roleType       requested role ("CUSTOMER" or "GYM_OWNER")
     */
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

    /**
     * Gets the unique registration ID.
     * 
     * @return the registration ID
     */
    public int getRegistrationId() {
        return registrationId;
    }

    /**
     * Sets the unique registration ID.
     * 
     * @param registrationId the registration ID
     */
    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    /**
     * Gets the full name of the registrant.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the registrant.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address for registration.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for registration.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password provided during registration.
     * 
     * @return the raw password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the registration password.
     * 
     * @param password the raw password string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the 10-digit mobile number.
     * 
     * @return the mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Sets the 10-digit mobile number.
     * 
     * @param mobileNumber the mobile number to set
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Gets the requested role type.
     * 
     * @return the role type string ("CUSTOMER" or "GYM_OWNER")
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * Sets the requested role type.
     * 
     * @param roleType the role type string
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * Gets the date on which the registration was created.
     * 
     * @return the registration date
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the registration date.
     * 
     * @param registrationDate the creation date
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Checks if the registration has been approved.
     * 
     * @return true if approved, false otherwise
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Sets the approval status of the registration.
     * 
     * @param approved true to approve, false to set as pending
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    /**
     * Gets the city location associated with the registration.
     * 
     * @return the city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city location associated with the registration.
     * 
     * @param city the city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the PAN number (for gym owners).
     * 
     * @return the pan number
     */
    public String getPanNumber() {
        return panNumber;
    }

    /**
     * Sets the PAN number (for gym owners).
     * 
     * @param panNumber the 10-character PAN
     */
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    /**
     * Gets the GST number (for gym owners).
     * 
     * @return the gst number
     */
    public String getGstNumber() {
        return gstNumber;
    }

    /**
     * Sets the GST number (for gym owners).
     * 
     * @param gstNumber the 15-character GSTIN
     */
    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    /**
     * Gets the Corporate Identification Number (for gym owners).
     * 
     * @return the cin
     */
    public String getCin() {
        return cin;
    }

    /**
     * Sets the Corporate Identification Number (for gym owners).
     * 
     * @param cin the 21-character CIN
     */
    public void setCin(String cin) {
        this.cin = cin;
    }

    /**
     * Gets the 12-digit Aadhaar number.
     * 
     * @return the aadhaar number
     */
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    /**
     * Sets the 12-digit Aadhaar number.
     * 
     * @param aadhaarNumber the Aadhaar string
     */
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
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
                ", gstNumber='" + gstNumber + '\'' +
                ", cin='" + cin + '\'' +
                ", aadhaarNumber='" + aadhaarNumber + '\'' +
                '}';
    }
}