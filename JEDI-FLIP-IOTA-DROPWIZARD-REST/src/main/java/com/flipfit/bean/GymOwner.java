package com.flipfit.bean;

/**
 * Represents a gym owner in the FlipFit system.
 * Extends the base {@link User} class with owner-specific professional and tax
 * details.
 * 
 * @author team IOTA
 */
public class GymOwner extends User {

    /**
     * Default constructor for GymOwner.
     */
    public GymOwner() {
    }

    /** 15-character Goods and Services Tax Identification Number. */
    private String gstNumber;

    /** 21-character Corporate Identification Number. */
    private String cin;

    /** 10-character Permanent Account Number. */
    private String panNumber;

    /** 12-digit Aadhaar number for identity verification. */
    private String aadhaarNumber;

    /**
     * Gets the owner's GST identification number.
     * 
     * @return the GST number string
     */
    public String getGstNumber() {
        return gstNumber;
    }

    /**
     * Sets the owner's GST identification number.
     * 
     * @param gstNumber the 15-character GSTIN
     */
    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    /**
     * Gets the Corporate Identification Number (CIN).
     * 
     * @return the CIN string
     */
    public String getCin() {
        return cin;
    }

    /**
     * Sets the Corporate Identification Number (CIN).
     * 
     * @param cin the 21-character CIN
     */
    public void setCin(String cin) {
        this.cin = cin;
    }

    /**
     * Gets the owner's Permanent Account Number (PAN).
     * 
     * @return the PAN string
     */
    public String getPanNumber() {
        return panNumber;
    }

    /**
     * Sets the owner's Permanent Account Number (PAN).
     * 
     * @param panNumber the 10-character PAN
     */
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    /**
     * Gets the owner's 12-digit Aadhaar number.
     * 
     * @return the Aadhaar number string
     */
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    /**
     * Sets the owner's 12-digit Aadhaar number.
     * 
     * @param aadhaarNumber the Aadhaar number string
     */
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
}
