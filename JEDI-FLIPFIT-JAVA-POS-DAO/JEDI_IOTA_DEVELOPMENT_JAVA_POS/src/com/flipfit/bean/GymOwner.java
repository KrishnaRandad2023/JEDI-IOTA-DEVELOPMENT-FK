package com.flipfit.bean;

/**
 * The Class GymOwner.
 * 
 * @author team IOTA
 * @ClassName "GymOwner"
 */
public class GymOwner extends User {
    /** The GST number. */
    private String gstNumber;

    /** The CIN. */
    private String cin;

    /** The PAN number. */
    private String panNumber;

    /** The aadhaar number. */
    private String aadhaarNumber;

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
