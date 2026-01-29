package com.flipfit.bean;

/**
 * Represents a gym customer in the system.
 * Extends the base {@link User} class with customer-specific attributes like
 * Aadhaar number.
 * 
 * @author team IOTA
 */
public class GymCustomer extends User {

    /**
     * Default constructor for GymCustomer.
     */
    public GymCustomer() {
    }

    /** 12-digit Aadhaar number for identity verification. */
    private String aadhaarNumber;

    /**
     * Gets the customer's 12-digit Aadhaar number.
     * 
     * @return the Aadhaar string
     */
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    /**
     * Sets the customer's 12-digit Aadhaar number.
     * 
     * @param aadhaarNumber the Aadhaar string
     */
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
}
