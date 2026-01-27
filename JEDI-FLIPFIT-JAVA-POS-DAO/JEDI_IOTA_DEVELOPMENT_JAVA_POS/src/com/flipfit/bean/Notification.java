package com.flipfit.bean;

import java.util.Date;

public class Notification {
    private int notificationId;
    private int userId;                    // which user receives notification
    private String message;                // notification message
    private Date timestamp;                // when notification was sent
    private boolean isRead;                // whether user has read it
    private String notificationType;       // type (BOOKING_CONFIRMED, WAITLIST_PROMOTED, etc.)
    
    // Constructors
    public Notification() {
        this.timestamp = new Date();
        this.isRead = false;
    }
    
    public Notification(int notificationId, int userId, String message, String notificationType) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.notificationType = notificationType;
        this.timestamp = new Date();
        this.isRead = false;
    }
    
    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }
    
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public boolean isRead() {
        return isRead;
    }
    
    public void setRead(boolean read) {
        isRead = read;
    }
    
    public String getNotificationType() {
        return notificationType;
    }
    
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    
    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                ", notificationType='" + notificationType + '\'' +
                '}';
    }
}