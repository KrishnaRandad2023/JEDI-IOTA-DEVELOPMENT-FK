package com.flipfit.business;

import java.util.List;
import com.flipfit.bean.Waitlist;

/**
 * The Interface WaitlistServiceInterface.
 *
 * @author team IOTA
 * @ClassName "WaitlistServiceInterface"
 */
public interface WaitlistServiceInterface {

    /**
     * Adds the to waitlist.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return true, if successful
     */
    boolean addToWaitlist(int userId, int slotId);

    /**
     * Removes the from waitlist.
     *
     * @param waitlistId the waitlist ID
     * @return true, if successful
     */
    boolean removeFromWaitlist(int waitlistId);

    /**
     * Gets the waitlist by slot.
     *
     * @param slotId the slot ID
     * @return the waitlist by slot
     */
    List<Waitlist> getWaitlistBySlot(int slotId);

    /**
     * Gets the waitlist position.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return the waitlist position
     */
    int getWaitlistPosition(int userId, int slotId);

    /**
     * Promote from waitlist.
     *
     * @param slotId the slot ID
     * @return true, if successful
     */
    boolean promoteFromWaitlist(int slotId);

    /**
     * Checks if is user in waitlist.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return true, if successful
     */
    boolean isUserInWaitlist(int userId, int slotId);
}
