package com.flipfit.bean;

import java.util.List;

/**
 * GymOwner class representing a gym owner in the system
 */
public class GymOwner extends User {
    private String panCard;
    private String aadhar;
    private String gstNumber;
    private boolean verificationStatus;
    private List<GymCenter> gymCenters;

    // Constructors
    public GymOwner() {
        super();
    }

    public GymOwner(int userId, String userName, String email, String password, String phoneNumber, 
                    String panCard, String aadhar, String gstNumber, boolean verificationStatus) {
        super(userId, userName, email, password, phoneNumber, Role.GYM_OWNER);
        this.panCard = panCard;
        this.aadhar = aadhar;
        this.gstNumber = gstNumber;
        this.verificationStatus = verificationStatus;
    }

    // Getters and Setters
    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public boolean isVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public List<GymCenter> getGymCenters() {
        return gymCenters;
    }

    public void setGymCenters(List<GymCenter> gymCenters) {
        this.gymCenters = gymCenters;
    }

    // Methods
    public void addGymCenter(GymCenter center) {
        // Method implementation
    }

    public void removeGymCenter(int centerId) {
        // Method implementation
    }

    public void updateGymCenter(GymCenter center) {
        // Method implementation
    }

    public void addSlot(Slot slot) {
        // Method implementation
    }

    public void viewMyCenter() {
        // Method implementation
    }
}
