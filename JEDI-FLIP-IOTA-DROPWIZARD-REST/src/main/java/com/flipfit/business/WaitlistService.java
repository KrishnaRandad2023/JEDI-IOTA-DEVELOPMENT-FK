package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.Waitlist;
import com.flipfit.exception.SlotNotAvailableException;

import com.flipfit.dao.WaitlistDAO;
import com.flipfit.dao.WaitlistDAOImpl;

/**
 * Service class for managing gym slot waitlists.
 * Handles adding users to waitlists when slots are full and promoting them when
 * bookings are cancelled.
 * 
 * @author team IOTA
 */
public class WaitlistService implements WaitlistServiceInterface {
    private WaitlistDAO waitlistDAO = new WaitlistDAOImpl();
    private BookingService bookingService;

    /**
     * Instantiates a new waitlist service and initializes the DAO.
     */
    public WaitlistService() {
        System.out.println("✅ WaitlistService initialized with Database DAO");
    }

    /**
     * Sets the waitlist DAO implementation.
     *
     * @param waitlistDAO the waitlist DAO to use
     */
    public void setWaitlistDAO(WaitlistDAO waitlistDAO) {
        this.waitlistDAO = waitlistDAO;
    }

    /**
     * Sets the booking service to handle promotions.
     *
     * @param bookingService the booking service to use
     */
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Adds a user to the waitlist for a specific slot.
     *
     * @param userId the ID of the user
     * @param slotId the ID of the slot
     * @return true if added successfully
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
     * Removes a user from the waitlist by waitlist ID.
     *
     * @param waitlistId the ID of the waitlist entry
     * @return true if removed successfully
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
     * Retrieves the current waitlist for a specific slot.
     *
     * @param slotId the ID of the slot
     * @return a list of waitlist entries
     */
    @Override
    public List<Waitlist> getWaitlistBySlot(int slotId) {
        return waitlistDAO.getWaitlistBySlot(slotId);
    }

    /**
     * Returns the user's priority position in the waitlist for a slot.
     *
     * @param userId the ID of the user
     * @param slotId the ID of the slot
     * @return the position (1-based), or -1 if not found
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
     * Promotes the first user in the waitlist for a slot to a confirmed booking.
     *
     * @param slotId the ID of the slot
     * @return true if a user was successfully promoted
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
     * Checks if a user is already in the waitlist for a specific slot.
     *
     * @param userId the ID of the user
     * @param slotId the ID of the slot
     * @return true if user is in waitlist
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
     * Retrieves waitlist entry by ID.
     *
     * @param waitlistId the ID of the entry
     * @return the Waitlist object
     */
    @Override
    public Waitlist getWaitlistById(int waitlistId) {
        return waitlistDAO.getWaitlistById(waitlistId);
    }

    /**
     * Retrieves all waitlist entries for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of waitlist entries
     */
    @Override
    public List<Waitlist> getWaitlistByUser(int userId) {
        return waitlistDAO.getWaitlistByUser(userId);
    }

    /**
     * Returns the total count of users waiting for a specific slot.
     *
     * @param slotId the ID of the slot
     * @return the count of waiting users
     */
    @Override
    public int getWaitlistCount(int slotId) {
        return waitlistDAO.getWaitlistBySlot(slotId).size();
    }

    /**
     * Clears all waitlist entries for a specific slot.
     *
     * @param slotId the ID of the slot
     * @return true if successful
     */
    @Override
    public boolean clearWaitlist(int slotId) {
        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);
        for (Waitlist entry : queue) {
            waitlistDAO.removeFromWaitlist(entry.getWaitlistId());
        }
        return true;
    }

    /**
     * Retrieves all waitlist entries in the system.
     *
     * @return an empty list (not implemented at global level)
     */
    @Override
    public List<Waitlist> getAllWaitlistEntries() {
        return new ArrayList<>();
    }

    /**
     * Re-calculates and updates priority positions for all users in a slot's
     * waitlist.
     *
     * @param slotId the ID of the slot
     */
    private void updatePriorityPositions(int slotId) {
        List<Waitlist> queue = waitlistDAO.getWaitlistBySlot(slotId);
        int position = 1;
        for (Waitlist entry : queue) {
            waitlistDAO.updateWaitlistPosition(entry.getWaitlistId(), position++);
        }
    }

    /**
     * Helper method to display waitlist entry details to the console.
     *
     * @param entry the waitlist entry to display
     */
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
     * Displays the entire waitlist for a specific slot to the console.
     *
     * @param slotId the ID of the slot
     */
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
     * Not implemented.
     */
    public void displayAllWaitlists() {
        System.out.println("Global waitlist view not implemented.");
    }

    /**
     * Displays a user's current waitlist status for all their entries.
     *
     * @param userId the ID of the user
     */
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