package com.flipfit.bean;

public enum BookingStatus {
    CONFIRMED("Confirmed", "Booking is confirmed"),
    CANCELLED("Cancelled", "Booking has been cancelled"),
    WAITLISTED("Waitlisted", "User is on waitlist"),
    COMPLETED("Completed", "Workout session completed"),
    NO_SHOW("No Show", "User did not attend");
    
    private final String displayName;
    private final String description;
    
    // Constructor
    BookingStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    // Getters
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}