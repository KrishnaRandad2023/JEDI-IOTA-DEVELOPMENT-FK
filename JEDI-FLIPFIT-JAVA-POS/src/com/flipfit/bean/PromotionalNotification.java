package com.flipfit.bean;

/**
 * PromotionalNotification class for promotional notifications
 */
public class PromotionalNotification extends Notification {
    private String promoCode;
    private String expiryDate;

    // Constructors
    public PromotionalNotification() {
        super();
    }

    public PromotionalNotification(int notificationId, int userId, String message, java.time.LocalDate date,
                                   String emailId, boolean isRead, String promoCode, String expiryDate) {
        super(notificationId, userId, message, date, emailId, isRead);
        this.promoCode = promoCode;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    // Methods
    public void sendPromo() {
        // Method implementation
    }
}
