package com.flipfit.business;

import com.flipfit.bean.Notification;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of INotificationService interface
 */
public class NotificationService implements INotificationService {
    
    private List<Notification> notifications;
    
    public NotificationService() {
        this.notifications = new ArrayList<>();
    }
    
    @Override
    public boolean sendNotification(Notification notification) {
        // TODO: Implement method to send notification
        System.out.println("Sending notification to user ID: " + notification.getUserId());
        notifications.add(notification);
        notification.send();
        return true;
    }
    
    @Override
    public List<Notification> getNotificationsByUserId(int userId) {
        // TODO: Implement method to get notifications by user ID
        List<Notification> userNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getUserId() == userId) {
                userNotifications.add(notification);
            }
        }
        return userNotifications;
    }
    
    @Override
    public boolean markAsRead(int notificationId) {
        // TODO: Implement method to mark notification as read
        System.out.println("Marking notification as read: " + notificationId);
        for (Notification notification : notifications) {
            if (notification.getNotificationId() == notificationId) {
                notification.markAsRead();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean sendBookingConfirmation(int bookingId, int userId) {
        // TODO: Implement method to send booking confirmation
        System.out.println("Sending booking confirmation for booking ID: " + bookingId);
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage("Your booking (ID: " + bookingId + ") has been confirmed!");
        return sendNotification(notification);
    }
}
