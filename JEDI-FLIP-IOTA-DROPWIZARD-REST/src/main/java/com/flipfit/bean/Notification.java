package com.flipfit.bean;

import java.util.Date;

/**
 * Represents a notification sent to a system user.
 * Tracks message content, recipient, delivery time, and read status.
 * 
 * @author team IOTA
 */
public class Notification {

    /** Unique persistent ID of the notification. */
    private int notificationId;

    /** ID of the user who is the recipient of this notification. */
    private int userId;

    /** The plain-text message content of the notification. */
    private String message;

    /** Timestamp when the notification was created. */
    private Date timestamp;

    /** Whether the user has seen/read the notification. */
    private boolean isRead;

    /** The category or type of notification (e.g., BOOKING_CONFIRMED). */
    private String notificationType;

    /**
     * Default constructor. Initializes timestamp to current time and sets isRead to
     * false.
     */
    public Notification() {
        this.timestamp = new Date();
        this.isRead = false;
    }

    /**
     * Parameterized constructor for creating a new notification.
     *
     * @param notificationId   unique persistent ID
     * @param userId           target recipient user ID
     * @param message          text content
     * @param notificationType category string
     */
    public Notification(int notificationId, int userId, String message, String notificationType) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.notificationType = notificationType;
        this.timestamp = new Date();
        this.isRead = false;
    }

    /**
     * Gets the unique ID of the notification.
     * 
     * @return the notificationId
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the unique ID of the notification.
     * 
     * @param notificationId the notificationId to set
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the recipient user ID.
     * 
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the recipient user ID.
     * 
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the notification message.
     * 
     * @return the message text
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the notification message.
     * 
     * @param message the message text to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the creation timestamp.
     * 
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the creation timestamp.
     * 
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Checks if the notification has been read.
     * 
     * @return true if read, false otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Sets the read status of the notification.
     * 
     * @param read true if read, false if unread
     */
    public void setRead(boolean read) {
        isRead = read;
    }

    /**
     * Gets the type category of the notification.
     * 
     * @return the notificationType string
     */
    public String getNotificationType() {
        return notificationType;
    }

    /**
     * Sets the type category of the notification.
     * 
     * @param notificationType the category string to set
     */
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