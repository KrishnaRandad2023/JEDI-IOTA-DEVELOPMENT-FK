package com.flipfit.bean;

public class WaitListNotification extends Notification {
    private int slotId;
    private int waitlistPosition;
    
    // Constructor
    public WaitListNotification() {
        super();
        this.setNotificationType("WAITLIST");
    }
    
    public WaitListNotification(int notificationId, int userId, String message, int slotId, int waitlistPosition) {
        super(notificationId, userId, message, "WAITLIST");
        this.slotId = slotId;
        this.waitlistPosition = waitlistPosition;
    }
    
    // Getters and Setters
    public int getSlotId() {
        return slotId;
    }
    
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
    
    public int getWaitlistPosition() {
        return waitlistPosition;
    }
    
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