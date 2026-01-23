package com.flipfit.bean;

import java.util.List;

/**
 * GymCenter class representing a gym center in the system
 */
public class GymCenter {
    private int centerId;
    private int ownerId;
    private String centerName;
    private String gstNumber;
    private String centerAddress;
    private String city;
    private int capacity;
    private double price;
    private List<Slot> slots;
    private boolean isApproved;

    // Constructors
    public GymCenter() {
    }

    public GymCenter(int centerId, int ownerId, String centerName, String gstNumber, String centerAddress,
                     String city, int capacity, double price, boolean isApproved) {
        this.centerId = centerId;
        this.ownerId = ownerId;
        this.centerName = centerName;
        this.gstNumber = gstNumber;
        this.centerAddress = centerAddress;
        this.city = city;
        this.capacity = capacity;
        this.price = price;
        this.isApproved = isApproved;
    }

    // Getters and Setters
    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
