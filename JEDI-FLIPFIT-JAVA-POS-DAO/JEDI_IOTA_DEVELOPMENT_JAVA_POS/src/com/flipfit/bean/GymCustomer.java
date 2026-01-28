package com.flipfit.bean;

/**
 * The Class GymCustomer.
 * 
 * @author team IOTA
 * @ClassName "GymCustomer"
 */
public class GymCustomer extends User {
    /** The aadhaar number. */
    private String aadhaarNumber;

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
}
