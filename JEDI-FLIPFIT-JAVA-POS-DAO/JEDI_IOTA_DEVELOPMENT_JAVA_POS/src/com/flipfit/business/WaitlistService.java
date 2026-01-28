package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.Waitlist;
import com.flipfit.exception.SlotNotAvailableException;

/// Classs level Comminting

/**
 * The Class WaitlistService.
 *
 * @author team IOTA
 * @ClassName "WaitlistService"
 */
import com.flipfit.dao.WaitlistDAO;
import com.flipfit.dao.WaitlistDAOImpl;

/**
 * The Class WaitlistService.
 * 
 * @author team IOTA
 */
public class WaitlistService implements WaitlistServiceInterface {
    private WaitlistDAO waitlistDAO = new WaitlistDAOImpl();
    private BookingService bookingService;

    public WaitlistService() {
        System.out.println("✅ WaitlistService initialized with Database DAO");
    }

    public void setWaitlistDAO(WaitlistDAO waitlistDAO) {
        this.waitlistDAO = waitlistDAO;
    }

    /**
     * Sets the booking service.
     *
     * @param bookingService the new booking service
     */
    // Set dependency (to be called after BookingService is created)
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Adds the to waitlist.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return true, if successful
     */
    @Override
    public boolean addToWaitlist(int userId, int slotId) {
        if (isUserInWaitlist(userId, slotId)) {
            System.out.println("⚠️ User already in waitlist for this slot!");
            return false;
        }

        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);

        Waitlist entry = new Waitlist();
        entry.setUserId(userId);
        entry.setSlotId(slotId);
        entry.setRequestDate(new Date());
        entry.setPriorityPosition(queue.size() + 1);

        boolean success = waitlistDAO.addToWaitlist(entry);
        if (success) {
            System.out.println("✅ Added to waitlist at position " + entry.getPriorityPosition());
        }
        return success;
    }

    /**
     * Removes the from waitlist.
     *
     * @param waitlistId the waitlist ID
     * @return true, if successful
     */
    @Override
    public boolean removeFromWaitlist(int waitlistId) {
        Waitlist entry = waitlistDAO.getWaitlistById(waitlistId);
        if (entry == null)
            return false;

        boolean success = waitlistDAO.removeFromWaitlist(waitlistId);
        if (success) {
            updatePriorityPositions(entry.getSlotId());
            System.out.println("✅ Removed from waitlist.");
        }
        return success;
    }

    /**
     * Gets the waitlist by slot.
     *
     * @param slotId the slot ID
     * @return the waitlist by slot
     */
    @Override
    public List<Waitlist> getWaitlistBySlot(int slotId) {
        return waitlistDAO.getWaitlistBySlot(slotId);
    }

    /**
     * Gets the waitlist position.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return the waitlist position
     */
    @Override
    public int getWaitlistPosition(int userId, int slotId) {
        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);
        for (Waitlist entry : queue) {
            if (entry.getUserId() == userId) {
                return entry.getPriorityPosition();
            }
        }
        return -1;
    }

    /**
     * Promote from waitlist.
     *
     * @param slotId the slot ID
     * @return true, if successful
     */
    @Override
    public boolean promoteFromWaitlist(int slotId) {
        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);
        if (queue.isEmpty())
            return false;

        Waitlist promoted = queue.get(0);
        waitlistDAO.removeFromWaitlist(promoted.getWaitlistId());

        if (bookingService != null) {
            try {
                bookingService.bookSlot(promoted.getUserId(), slotId, new Date());
                System.out.println("✅ User promoted from waitlist: " + promoted.getUserId());
            } catch (SlotNotAvailableException e) {
                System.out.println("❌ Promotion failed: " + e.getMessage());
            }
        }

        updatePriorityPositions(slotId);
        return true;
    }

    /**
     * Checks if is user in waitlist.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return true, if successful
     */
    @Override
    public boolean isUserInWaitlist(int userId, int slotId) {
        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);
        for (Waitlist entry : queue) {
            if (entry.getUserId() == userId)
                return true;
        }
        return false;
    }

    /**
     * Gets the waitlist by ID.
     *
     * @param waitlistId the waitlist ID
     * @return the waitlist by ID
     */
    @Override
    public Waitlist getWaitlistById(int waitlistId) {
        return waitlistDAO.getWaitlistById(waitlistId);
    }

    /**
     * Gets the waitlist by user.
     *
     * @param userId the user ID
     * @return the waitlist by user
     */
    @Override
    public List<Waitlist> getWaitlistByUser(int userId) {
        return waitlistDAO.getWaitlistByUser(userId);
    }

    /**
     * Gets the waitlist count.
     *
     * @param slotId the slot ID
     * @return the waitlist count
     */
    @Override
    public int getWaitlistCount(int slotId) {
        return waitlistDAO.getWaitlistBySlot(slotId).size();
    }

    @Override
    public boolean clearWaitlist(int slotId) {
        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);
        for (Waitlist entry : queue) {
            waitlistDAO.removeFromWaitlist(entry.getWaitlistId());
        }
        return true;
    }

    /**
     * Gets the all waitlist entries.
     *
     * @return the all waitlist entries
     */
    @Override
    public List<Waitlist> getAllWaitlistEntries() {
        return new ArrayList<>(); // Should probably add a DAO method for this if needed
    }

    /**
     * Update priority positions.
     *
     * @param slotId the slot ID
     */
    private void updatePriorityPositions(int slotId) {
        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);
        int position = 1;
        for (Waitlist entry : queue) {
            waitlistDAO.updateWaitlistPosition(entry.getWaitlistId(), position++);
        }
    }

    /**
     * Display waitlist entry.
     *
     * @param entry the entry
     */
    // 13. Display waitlist entry (helper method)
    public void displayWaitlistEntry(Waitlist entry) {
        if (entry != null) {
            System.out.println("Waitlist ID: " + entry.getWaitlistId());
            System.out.println("User ID: " + entry.getUserId());
            System.out.println("Slot ID: " + entry.getSlotId());
            System.out.println("Position: " + entry.getPriorityPosition());
            System.out.println("Request Date: " + entry.getRequestDate());
            System.out.println("---");
        }
    }

    /**
     * Display waitlist by slot.
     *
     * @param slotId the slot ID
     */
    // 14. Display waitlist for a slot
    public void displayWaitlistBySlot(int slotId) {
        List<Waitlist> waitlist = getWaitlistBySlot(slotId);

        if (waitlist.isEmpty()) {
            System.out.println("No waitlist entries for slot ID: " + slotId);
        } else {
            System.out.println("\n=== Waitlist for Slot ID: " + slotId + " ===");
            System.out.println("Total waiting: " + waitlist.size());
            for (Waitlist entry : waitlist) {
                displayWaitlistEntry(entry);
            }
        }
    }

    /**
     * Display all waitlists.
     */
    public void displayAllWaitlists() {
        System.out.println("Global waitlist view not implemented.");
    }

    /**
     * Display user waitlist status.
     *
     * @param userId the user ID
     */
    // 16. Display user's waitlist status
    public void displayUserWaitlistStatus(int userId) {
        List<Waitlist> userWaitlist = getWaitlistByUser(userId);

        if (userWaitlist.isEmpty()) {
            System.out.println("You are not in any waitlist.");
        } else {
            System.out.println("\n=== Your Waitlist Status ===");
            for (Waitlist entry : userWaitlist) {
                System.out.println("Slot ID: " + entry.getSlotId());
                System.out.println("Position: " + entry.getPriorityPosition());
                System.out.println("Waiting since: " + entry.getRequestDate());
                System.out.println("---");
            }
        }
    }
}