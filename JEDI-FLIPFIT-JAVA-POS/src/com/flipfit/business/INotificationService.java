package com.flipfit.business;

import com.flipfit.bean.Notification;
import java.util.List;

/**
 * Interface for Notification Service operations
 */
public interface INotificationService {
    
    /**
     * Send a notification to a user
     * @param notification Notification object
     * @return boolean indicating success
     */
    boolean sendNotification(Notification notification);
    
    /**
     * Get all notifications for a user
     * @param userId User ID
     * @return List of notifications
     */
    List<Notification> getNotificationsByUserId(int userId);
    
    /**
     * Mark notification as read
     * @param notificationId Notification ID
     * @return boolean indicating success
     */
    boolean markAsRead(int notificationId);
    
    /**
     * Send booking confirmation notification
     * @param bookingId Booking ID
     * @param userId User ID
     * @return boolean indicating success
     */
    boolean sendBookingConfirmation(int bookingId, int userId);
}
