package com.flipfit.dao;

import com.flipfit.bean.Waitlist;
import java.util.List;

/**
 * Interface for Waitlist DAO
 * 
 * @author team IOTA
 */
public interface WaitlistDAO {
    boolean addToWaitlist(Waitlist waitlist);

    boolean removeFromWaitlist(int waitlistId);

    List<Waitlist> getWaitlistBySlot(int slotId);

    List<Waitlist> getWaitlistByUser(int userId);

    Waitlist getWaitlistById(int waitlistId);

    boolean updateWaitlistPosition(int waitlistId, int newPosition);
}
