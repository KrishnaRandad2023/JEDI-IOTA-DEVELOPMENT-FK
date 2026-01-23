package com.flipfit.bean;

/**
 * WaitlistNotification class for waitlist notifications
 */
public class WaitlistNotification extends Notification {
    private int waitlistId;
    private int slotPosition;

    // Constructors
    public WaitlistNotification() {
        super();
    }

    public WaitlistNotification(int notificationId, int userId, String message, java.time.LocalDate date,
                               String emailId, boolean isRead, int waitlistId, int slotPosition) {
        super(notificationId, userId, message, date, emailId, isRead);
        this.waitlistId = waitlistId;
        this.slotPosition = slotPosition;
    }

    // Getters and Setters
    public int getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(int waitlistId) {
        this.waitlistId = waitlistId;
    }

    public int getSlotPosition() {
        return slotPosition;
    }

    public void setSlotPosition(int slotPosition) {
        this.slotPosition = slotPosition;
    }

    // Methods
    public void notifyWaitlist() {
        // Method implementation
    }
}
