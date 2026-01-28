package com.flipfit.bean;

import java.util.Date;

/**
 * The Class Registration.
 * 
 * @author team IOTA
 * @ClassName "Registration"
 */
public class Registration {
    /** The registration ID. */
    private int registrationId;

    /** The name. */
    private String name;

    /** The email. */
    private String email;

    /** The password. */
    private String password;

    /** The mobile number. */
    private String mobileNumber;

    /** The role type ("CUSTOMER" or "GYM_OWNER"). */
    private String roleType;

    /** The registration date. */
    private Date registrationDate;

    /** The approval status. */
    private boolean isApproved;

    /** The city (for gym owners). */
    private String city;

    /** The PAN number (for gym owners). */
    private String panNumber;

    /** The GST Number. */
    private String gstNumber;

    /** The CIN. */
    private String cin;

    /** The Aadhaar Number. */
    private String aadhaarNumber;

    /**
     * Instantiates a new registration.
     */
    public Registration() {
        this.registrationDate = new Date();
        this.isApproved = false;
    }

    /**
     * Instantiates a new registration.
     *
     * @param registrationId the registration ID
     * @param name           the name
     * @param email          the email
     * @param password       the password
     * @param mobileNumber   the mobile number
     * @param roleType       the role type
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
     * Gets the registration ID.
     * 
     * @return the registration ID
     */
    public int getRegistrationId() {
        return registrationId;
    }

    /**
     * Sets the registration ID.
     * 
     * @param registrationId the new registration ID
     */
    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
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
     * Gets the role type.
     * 
     * @return the role type
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * Sets the role type.
     * 
     * @param roleType the new role type
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * Gets the registration date.
     * 
     * @return the registration date
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the registration date.
     * 
     * @param registrationDate the new registration date
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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
     * @param approved the new approved status
     */
    public void setApproved(boolean approved) {
        isApproved = approved;
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
     * Gets the pan number.
     * 
     * @return the pan number
     */
    public String getPanNumber() {
        return panNumber;
    }

    /**
     * Sets the pan number.
     * 
     * @param panNumber the new pan number
     */
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    /**
     * Gets the gst number.
     * 
     * @return the gst number
     */
    public String getGstNumber() {
        return gstNumber;
    }

    /**
     * Sets the gst number.
     * 
     * @param gstNumber the new gst number
     */
    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    /**
     * Gets the cin.
     * 
     * @return the cin
     */
    public String getCin() {
        return cin;
    }

    /**
     * Sets the cin.
     * 
     * @param cin the new cin
     */
    public void setCin(String cin) {
        this.cin = cin;
    }

    /**
     * Gets the aadhaar number.
     * 
     * @return the aadhaar number
     */
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    /**
     * Sets the aadhaar number.
     * 
     * @param aadhaarNumber the new aadhaar number
     */
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    /**
     * To string.
     * 
     * @return the string
     */
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