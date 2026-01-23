package com.flipfit.bean;

/**
 * GymCustomer class representing a customer in the system
 */
public class GymCustomer extends User {
    private String address;
    private String pincode;

    // Constructors
    public GymCustomer() {
        super();
    }

    public GymCustomer(int userId, String userName, String email, String password, String phoneNumber,
                       String address, String pincode) {
        super(userId, userName, email, password, phoneNumber, Role.CUSTOMER);
        this.address = address;
        this.pincode = pincode;
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    // Methods
    public void viewCenterByArea(String area) {
        // Method implementation
    }

    public void checkAvailability(int centerId) {
        // Method implementation
    }

    public void makeBooking(Booking booking) {
        // Method implementation
    }

    public void cancelBooking(int bookingId) {
        // Method implementation
    }
}
