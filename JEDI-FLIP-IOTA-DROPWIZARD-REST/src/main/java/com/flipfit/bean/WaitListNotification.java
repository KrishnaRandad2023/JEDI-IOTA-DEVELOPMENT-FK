package com.flipfit.bean;

/**
 * Specialized notification for waitlist-related updates.
 * Includes slot details and the user's current position in the queue.
 * 
 * @author team IOTA
 */
public class WaitListNotification extends Notification {

    /** Unique persistent ID of the slot associated with this notification. */
    private int slotId;

    /** The user's current 1-based position in the waitlist. */
    private int waitlistPosition;

    /**
     * Default constructor for WaitListNotification.
     * Sets the notification type to "WAITLIST".
     */
    public WaitListNotification() {
        super();
        this.setNotificationType("WAITLIST");
    }

    /**
     * Parameterized constructor for WaitListNotification.
     * 
     * @param notificationId   unique ID of the notification
     * @param userId           ID of the user receiving the notification
     * @param message          notification message content
     * @param slotId           ID of the gym slot
     * @param waitlistPosition current position in the waitlist
     */
    public WaitListNotification(int notificationId, int userId, String message, int slotId, int waitlistPosition) {
        super(notificationId, userId, message, "WAITLIST");
        this.slotId = slotId;
        this.waitlistPosition = waitlistPosition;
    }

    /**
     * Gets the ID of the slot associated with this notification.
     * 
     * @return the slot ID
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * Sets the ID of the slot associated with this notification.
     * 
     * @param slotId the gym slot ID
     */
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    /**
     * Gets the user's current 1-based position in the waitlist.
     * 
     * @return the waitlist position
     */
    public int getWaitlistPosition() {
        return waitlistPosition;
    }

    /**
     * Sets the user's current 1-based position in the waitlist.
     * 
     * @param waitlistPosition the new position
     */
    public void setWaitlistPosition(int waitlistPosition) {
        this.waitlistPosition = waitlistPosition;
    }

    @Override
    public String toString() {
        return "WaitListNotification{" +
                "slotId=" + slotId +
                ", waitlistPosition=" + waitlistPosition +
                ", " + super.toString() +
                '}';
    }
}