package com.flipfit.business;

import java.util.List;

import com.flipfit.bean.Waitlist;

public class WaitlistService implements WaitlistServiceInterface {
	@Override
    public boolean addToWaitlist(int userId, int slotId) {
        // TODO: Add user to waitlist
        // TODO: Assign priority position
        return false;
    }
    
    @Override
    public boolean removeFromWaitlist(int waitlistId) {
        // TODO: Remove user from waitlist
        return false;
    }
    
    @Override
    public List<Waitlist> getWaitlistBySlot(int slotId) {
        // TODO: Get all waitlist entries for slot, ordered by priority
        return null;
    }
    
    @Override
    public int getWaitlistPosition(int userId, int slotId) {
        // TODO: Return user's position in waitlist (1 = first in line)
        return -1;
    }
    
    @Override
    public boolean promoteFromWaitlist(int slotId) {
        // TODO: Move first person from waitlist to confirmed booking
        // TODO: Send notification to user
        return false;
    }
    
    @Override
    public boolean isUserInWaitlist(int userId, int slotId) {
        // TODO: Check if user is already in waitlist
        return false;
    }
}
