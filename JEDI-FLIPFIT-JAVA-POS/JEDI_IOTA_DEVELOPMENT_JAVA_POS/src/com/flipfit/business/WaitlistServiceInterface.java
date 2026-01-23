package com.flipfit.business;

import java.util.List;

import com.flipfit.bean.Waitlist;

public interface WaitlistServiceInterface {
boolean addToWaitlist(int userId, int slotId);
    
    // Remove user from waitlist
    boolean removeFromWaitlist(int waitlistId);
    
    // Get all waitlist entries for a specific slot
    List<Waitlist> getWaitlistBySlot(int slotId);
    
    // Get user's position in waitlist
    int getWaitlistPosition(int userId, int slotId);
    
    // Promote next person from waitlist when slot becomes available
    boolean promoteFromWaitlist(int slotId);
    
    // Check if user is in waitlist for a slot
    boolean isUserInWaitlist(int userId, int slotId);
}
