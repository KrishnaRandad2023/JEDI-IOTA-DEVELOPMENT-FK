package com.flipfit.bean;

/**
 * Specialized notification for promotional messages and offers.
 * 
 * @author team IOTA
 */
public class PromotionalNotification extends Notification {

    /**
     * Default constructor for PromotionalNotification.
     * Sets the notification type to "PROMOTIONAL".
     */
    public PromotionalNotification() {
        super();
        this.setNotificationType("PROMOTIONAL");
    }
}
