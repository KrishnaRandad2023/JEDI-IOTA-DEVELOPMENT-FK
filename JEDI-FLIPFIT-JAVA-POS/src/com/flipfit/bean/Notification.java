package com.flipfit.bean;

import java.time.LocalDate;

/**
 * Notification class representing a notification sent to users
 */
public class Notification {
    private int notificationId;
    private int userId;
    private String message;
    private LocalDate date;
    private String emailId;
    private boolean isRead;

    // Constructors
    public Notification() {
    }

    public Notification(int notificationId, int userId, String message, LocalDate date, String emailId, boolean isRead) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.date = date;
        this.emailId = emailId;
        this.isRead = isRead;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    // Methods
    public void send() {
        // Method implementation
    }

    public void markAsRead() {
        // Method implementation
    }
}
