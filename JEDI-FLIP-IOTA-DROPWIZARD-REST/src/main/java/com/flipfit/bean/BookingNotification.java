package com.flipfit.bean;

/**
 * Specialized notification for confirming successful slot bookings.
 * 
 * @author team IOTA
 */
public class BookingNotification extends Notification {

    /**
     * Default constructor for BookingNotification.
     * Sets the notification type to "BOOKING".
     */
    public BookingNotification() {
        super();
        this.setNotificationType("BOOKING");
    }
}
