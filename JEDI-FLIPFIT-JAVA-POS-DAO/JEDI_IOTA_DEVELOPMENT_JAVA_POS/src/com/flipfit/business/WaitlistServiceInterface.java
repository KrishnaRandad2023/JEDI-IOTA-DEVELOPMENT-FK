package com.flipfit.business;

import java.util.List;
import com.flipfit.bean.Waitlist;

/**
 * Interface for Waitlist Service Operations.
 * Manages users who are in the waitlist for specific gym slots.
 * 
 * @author team IOTA
 */
public interface WaitlistServiceInterface {

    /**
     * Adds a user to the waitlist for a specific slot.
     *
     * @param userId the ID of the user
     * @param slotId the ID of the slot
     * @return true if added successfully, false otherwise
     */
    boolean addToWaitlist(int userId, int slotId);

    /**
     * Removes an entry from the waitlist given its ID.
     *
     * @param waitlistId the ID of the waitlist entry to remove
     * @return true if removal is successful, false otherwise
     */
    boolean removeFromWaitlist(int waitlistId);

    /**
     * Retrieves all waitlist entries for a specific slot, ordered by priority.
     *
     * @param slotId the ID of the slot
     * @return a list of waitlist entries for the slot
     */
    List<Waitlist> getWaitlistBySlot(int slotId);

    /**
     * Gets the priority position of a user in the waitlist for a specific slot.
     *
     * @param userId the ID of the user
     * @param slotId the ID of the slot
     * @return the position of the user (1-indexed), or -1 if not in waitlist
     */
    int getWaitlistPosition(int userId, int slotId);

    /**
     * Promotes the top person in the waitlist to a confirmed booking for the slot.
     *
     * @param slotId the ID of the slot where a vacancy occurred
     * @return true if promotion was successful, false otherwise
     */
    boolean promoteFromWaitlist(int slotId);

    /**
     * Checks if a user is currently in the waitlist for a specific slot.
     *
     * @param userId the ID of the user
     * @param slotId the ID of the slot
     * @return true if the user is in the waitlist, false otherwise
     */
    boolean isUserInWaitlist(int userId, int slotId);

    /**
     * Retrieves a waitlist entry by its unique ID.
     *
     * @param waitlistId the ID of the waitlist entry
     * @return the Waitlist object, or null if not found
     */
    Waitlist getWaitlistById(int waitlistId);

    /**
     * Retrieves all waitlist entries associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of waitlist entries for the user
     */
    List<Waitlist> getWaitlistByUser(int userId);

    /**
     * Retrieves the total count of users in the waitlist for a specific slot.
     *
     * @param slotId the ID of the slot
     * @return the number of users waiting for the slot
     */
    int getWaitlistCount(int slotId);

    /**
     * Retrieves all waitlist entries in the system.
     *
     * @return a list containing all waitlist records
     */
    List<Waitlist> getAllWaitlistEntries();

    /**
     * Clears all waitlist entries for a specific slot.
     *
     * @param slotId the ID of the slot
     * @return true if the waitlist was cleared successfully
     */
    boolean clearWaitlist(int slotId);
}
