package com.flipfit.bean;

/**
 * Enum representing the current life-cycle status of a gym slot booking.
 * 
 * @author team IOTA
 */
public enum BookingStatus {

    /** The booking has been recorded and the seat is reserved. */
    CONFIRMED("Confirmed", "Booking is confirmed"),

    /** The customer has cancelled the booking and released the seat. */
    CANCELLED("Cancelled", "Booking has been cancelled"),

    /** The slot was full and the user is waiting for a cancellation. */
    WAITLISTED("Waitlisted", "User is on waitlist"),

    /** The booked workout session has taken place. */
    COMPLETED("Completed", "Workout session completed"),

    /** The user did not attend the booked session. */
    NO_SHOW("No Show", "User did not attend");

    private final String displayName;
    private final String description;

    /**
     * Parameterized constructor for BookingStatus enum.
     * 
     * @param displayName user-friendly name of the status
     * @param description brief explanation of the status
     */
    BookingStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    // Getters
    /**
     * Gets the user-friendly name of the booking status.
     * 
     * @return the display name (e.g., "Confirmed")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets a brief explanation of the booking status.
     * 
     * @return the status description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}