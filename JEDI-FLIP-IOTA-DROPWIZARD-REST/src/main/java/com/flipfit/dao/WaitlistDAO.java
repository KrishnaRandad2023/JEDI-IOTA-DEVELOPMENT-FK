package com.flipfit.dao;

import com.flipfit.bean.Waitlist;
import java.util.List;

/**
 * Data Access Object interface for managing waitlist-related persistent data.
 * Handles adding users to slots that are full and promoting them when space
 * becomes available.
 * 
 * @author team IOTA
 */
public interface WaitlistDAO {

    /**
     * Adds a user to the waitlist for a specific slot.
     * 
     * @param waitlist the Waitlist object containing user and slot details
     * @return true if successfully added
     */
    boolean addToWaitlist(Waitlist waitlist);

    /**
     * Removes an entry from the waitlist after promotion or cancellation.
     * 
     * @param waitlistId the unique persistent ID of the waitlist entry
     * @return true if deletion was successful
     */
    boolean removeFromWaitlist(int waitlistId);

    /**
     * Retrieves all waitlist entries for a specific slot, ordered by priority.
     * 
     * @param slotId the unique slot ID
     * @return an ordered list of waitlist participants
     */
    List<Waitlist> getWaitlistBySlot(int slotId);

    /**
     * Retrieves all waitlist entries for a specific user.
     * 
     * @param userId the unique user ID
     * @return a list of slots where the user is waitlisted
     */
    List<Waitlist> getWaitlistByUser(int userId);

    /**
     * Retrieves a single waitlist entry by its ID.
     * 
     * @param waitlistId the persistent waitlist ID
     * @return the Waitlist object, or null if not found
     */
    Waitlist getWaitlistById(int waitlistId);

    /**
     * Updates the priority position of a user in the waitlist.
     * 
     * @param waitlistId  the persistent waitlist ID
     * @param newPosition the new zero-based or one-based priority
     * @return true if the update was successful
     */
    boolean updateWaitlistPosition(int waitlistId, int newPosition);
}
