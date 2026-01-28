package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.BookingNotDoneException;

/// Classs level Comminting

/**
 * The Class CustomerService.
 *
 * @author Shravya
 * @ClassName "CustomerService"
 */
public class CustomerService {

    /** The gym service. */
    // References to other services (Customer service uses existing services)
    private GymService gymService;

    /** The slot service. */
    private SlotService slotService;

    /** The booking service. */
    private BookingService bookingService;

    /** The waitlist service. */
    private WaitlistService waitlistService;

    /** The gym user service. */
    private GymUserService gymUserService;

    /**
     * Instantiates a new customer service.
     */
    public CustomerService() {
        System.out.println("✅ CustomerService initialized");
    }

    /**
     * Sets the gym service.
     *
     * @param gymService the new gym service
     */
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    /**
     * Sets the slot service.
     *
     * @param slotService the new slot service
     */
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }

    /**
     * Sets the booking service.
     *
     * @param bookingService the new booking service
     */
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Sets the waitlist service.
     *
     * @param waitlistService the new waitlist service
     */
    public void setWaitlistService(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }

    /**
     * Sets the gym user service.
     *
     * @param gymUserService the new gym user service
     */
    public void setGymUserService(GymUserService gymUserService) {
        this.gymUserService = gymUserService;
    }

    // ==================== GYM BROWSING OPERATIONS ====================

    /**
     * View gyms in city.
     *
     * @param city the city
     * @return the list
     */
    public List<GymCenter> viewGymsInCity(String city) {
        if (gymService == null) {
            System.out.println("❌ GymService not available!");
            return new ArrayList<>();
        }

        return gymService.viewAllCenters(city);
    }

    /**
     * View all gyms.
     *
     * @return the list
     */
    public List<GymCenter> viewAllGyms() {
        if (gymService == null) {
            return new ArrayList<>();
        }

        return gymService.getApprovedCenters();
    }

    /**
     * Gets the gym center details.
     *
     * @param centerId the center ID
     * @return the gym center details
     */
    public GymCenter getGymCenterDetails(int centerId) {
        if (gymService == null) {
            return null;
        }

        GymCenter center = gymService.getCenterById(centerId);

        // Only return if approved
        if (center != null && center.isApproved()) {
            return center;
        }

        return null;
    }

    /**
     * Search gyms by name.
     *
     * @param searchTerm the search term
     * @return the list
     */
    public List<GymCenter> searchGymsByName(String searchTerm) {
        if (gymService == null) {
            return new ArrayList<>();
        }

        List<GymCenter> allGyms = gymService.getApprovedCenters();
        List<GymCenter> matchingGyms = new ArrayList<>();

        String lowerSearchTerm = searchTerm.toLowerCase();

        for (GymCenter center : allGyms) {
            if (center.getCenterName().toLowerCase().contains(lowerSearchTerm)) {
                matchingGyms.add(center);
            }
        }

        return matchingGyms;
    }

    // ==================== SLOT BROWSING OPERATIONS ====================

    /**
     * View slots for gym.
     *
     * @param centerId the center ID
     * @return the list
     */
    public List<Slot> viewSlotsForGym(int centerId) {
        if (slotService == null) {
            return new ArrayList<>();
        }

        // Verify gym is approved
        GymCenter center = getGymCenterDetails(centerId);
        if (center == null) {
            System.out.println("❌ Gym center not found or not approved!");
            return new ArrayList<>();
        }

        return slotService.getSlotsByCenter(centerId);
    }

    /**
     * View available slots for gym.
     *
     * @param centerId the center ID
     * @return the list
     */
    public List<Slot> viewAvailableSlotsForGym(int centerId) {
        if (slotService == null) {
            return new ArrayList<>();
        }

        // Verify gym is approved
        GymCenter center = getGymCenterDetails(centerId);
        if (center == null) {
            System.out.println("❌ Gym center not found or not approved!");
            return new ArrayList<>();
        }

        return slotService.getAvailableSlotsByCenter(centerId);
    }

    /**
     * Gets the slot details.
     *
     * @param slotId the slot ID
     * @return the slot details
     */
    public Slot getSlotDetails(int slotId) {
        if (slotService == null) {
            return null;
        }

        return slotService.getSlotById(slotId);
    }

    // ==================== BOOKING OPERATIONS ====================

    /**
     * Book slot.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return true, if successful
     * @throws UserNotFoundException     the user not found exception
     * @throws SlotNotAvailableException the slot not available exception
     */
    public boolean bookSlot(int userId, int slotId) throws UserNotFoundException, SlotNotAvailableException {
        if (bookingService == null || slotService == null) {
            System.out.println("❌ Required services not available!");
            return false;
        }

        // Verify user is a customer
        if (gymUserService != null) {
            User user = gymUserService.getUserById(userId);
            if (user == null) {
                System.out.println("❌ User not found!");
                throw new UserNotFoundException("User with ID " + userId + " not found!");
            }

            if (!(user instanceof GymCustomer)) {
                System.out.println("❌ Only customers can book slots!");
                return false;
            }
        }

        // Check if slot exists
        Slot slot = slotService.getSlotById(slotId);
        if (slot == null) {
            System.out.println("❌ Slot not found!");
            return false;
        }

        // Check if user already booked this slot
        if (bookingService.hasUserBookedSlot(userId, slotId)) {
            System.out.println("❌ You have already booked this slot!");
            return false;
        }

        // FIXED: Check availability BEFORE booking to return accurate result
        boolean hasSeats = slotService.hasAvailableSeats(slotId);

        // Try to book
        bookingService.bookSlot(userId, slotId, new Date());

        // FIXED: Return true only if seats were available (confirmed booking)
        // If no seats, user was added to waitlist, so return false
        return hasSeats;
    }

    /**
     * Cancel booking.
     *
     * @param userId    the user ID
     * @param bookingId the booking ID
     * @return true, if successful
     * @throws BookingNotDoneException the booking not done exception
     */
    public boolean cancelBooking(int userId, int bookingId) throws BookingNotDoneException {
        if (bookingService == null) {
            System.out.println("❌ BookingService not available!");
            return false;
        }

        // Get booking
        Booking booking = bookingService.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("❌ Booking not found!");
            throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
        }

        // Verify booking belongs to user
        if (booking.getUserId() != userId) {
            System.out.println("❌ You can only cancel your own bookings!");
            return false;
        }

        // Check if already cancelled
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("⚠️ Booking is already cancelled!");
            return false;
        }

        // Cancel booking
        bookingService.cancelBooking(bookingId);
        return true;
    }

    /**
     * View my bookings.
     *
     * @param userId the user ID
     * @return the list
     */
    public List<Booking> viewMyBookings(int userId) {
        if (bookingService == null) {
            return new ArrayList<>();
        }

        return bookingService.getBookingsByUser(userId);
    }

    /**
     * View my active bookings.
     *
     * @param userId the user ID
     * @return the list
     */
    public List<Booking> viewMyActiveBookings(int userId) {
        if (bookingService == null) {
            return new ArrayList<>();
        }

        return bookingService.getActiveBookingsByUser(userId);
    }

    /**
     * View my booking history.
     *
     * @param userId the user ID
     * @return the list
     */
    public List<Booking> viewMyBookingHistory(int userId) {
        List<Booking> allBookings = viewMyBookings(userId);
        List<Booking> history = new ArrayList<>();

        for (Booking booking : allBookings) {
            if (booking.getStatus() == BookingStatus.CANCELLED ||
                    booking.getStatus() == BookingStatus.COMPLETED) {
                history.add(booking);
            }
        }

        return history;
    }

    // ==================== WAITLIST OPERATIONS ====================

    /**
     * View my waitlist status.
     *
     * @param userId the user ID
     * @return the list
     */
    public List<Waitlist> viewMyWaitlistStatus(int userId) {
        if (waitlistService == null) {
            return new ArrayList<>();
        }

        return waitlistService.getWaitlistByUser(userId);
    }

    /**
     * Gets the my waitlist position.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return the my waitlist position
     */
    public int getMyWaitlistPosition(int userId, int slotId) {
        if (waitlistService == null) {
            return -1;
        }

        return waitlistService.getWaitlistPosition(userId, slotId);
    }

    /**
     * Removes the from waitlist.
     *
     * @param userId     the user ID
     * @param waitlistId the waitlist ID
     * @return true, if successful
     */
    public boolean removeFromWaitlist(int userId, int waitlistId) {
        if (waitlistService == null) {
            System.out.println("❌ WaitlistService not available!");
            return false;
        }

        // Verify waitlist entry belongs to user
        Waitlist entry = waitlistService.getWaitlistById(waitlistId);

        if (entry == null) {
            System.out.println("❌ Waitlist entry not found!");
            return false;
        }

        if (entry.getUserId() != userId) {
            System.out.println("❌ You can only remove yourself from waitlist!");
            return false;
        }

        return waitlistService.removeFromWaitlist(waitlistId);
    }

    // ==================== PROFILE & STATISTICS ====================

    /**
     * Update my profile.
     *
     * @param user the user
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean updateMyProfile(User user) throws UserNotFoundException {
        if (gymUserService == null) {
            System.out.println("❌ GymUserService not available!");
            return false;
        }

        // Verify user is a customer
        if (!(user instanceof GymCustomer)) {
            System.out.println("❌ Invalid user type!");
            return false;
        }

        return gymUserService.updateUserProfile(user);
    }

    /**
     * Change my password.
     *
     * @param userId      the user ID
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean changeMyPassword(int userId, String oldPassword, String newPassword) throws UserNotFoundException {
        if (gymUserService == null) {
            System.out.println("❌ GymUserService not available!");
            return false;
        }

        return gymUserService.changePassword(userId, oldPassword, newPassword);
    }

    /**
     * Gets the my statistics.
     *
     * @param userId the user ID
     * @return the my statistics
     */
    public Map<String, Integer> getMyStatistics(int userId) {
        Map<String, Integer> stats = new HashMap<>();

        List<Booking> myBookings = viewMyBookings(userId);
        List<Booking> activeBookings = viewMyActiveBookings(userId);
        List<Waitlist> myWaitlist = viewMyWaitlistStatus(userId);

        // Count by status
        int confirmedCount = 0;
        int cancelledCount = 0;
        int completedCount = 0;

        for (Booking booking : myBookings) {
            switch (booking.getStatus()) {
                case CONFIRMED -> confirmedCount++;
                case CANCELLED -> cancelledCount++;
                case COMPLETED -> completedCount++;
                default -> {
                }
            }
        }

        stats.put("Total Bookings", myBookings.size());
        stats.put("Active Bookings", activeBookings.size());
        stats.put("Confirmed Bookings", confirmedCount);
        stats.put("Cancelled Bookings", cancelledCount);
        stats.put("Completed Bookings", completedCount);
        stats.put("Waitlist Entries", myWaitlist.size());

        return stats;
    }

    // ==================== DISPLAY METHODS ====================

    /**
     * Display gyms in city.
     *
     * @param city the city
     */
    public void displayGymsInCity(String city) {
        List<GymCenter> gyms = viewGymsInCity(city);

        if (gyms.isEmpty()) {
            System.out.println("No gyms found in " + city);
        } else {
            System.out.println("\n=== GYMS IN " + city.toUpperCase() + " ===");
            for (GymCenter center : gyms) {
                if (gymService != null) {
                    gymService.displayCenter(center);
                }
            }
        }
    }

    /**
     * Display available slots for gym.
     *
     * @param centerId the center ID
     */
    public void displayAvailableSlotsForGym(int centerId) {
        // Get gym name
        GymCenter center = getGymCenterDetails(centerId);
        String gymName = center != null ? center.getCenterName() : "Unknown Gym";

        List<Slot> availableSlots = viewAvailableSlotsForGym(centerId);

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for " + gymName);
        } else {
            System.out.println("\n=== AVAILABLE SLOTS FOR " + gymName + " ===");
            for (Slot slot : availableSlots) {
                if (slotService != null) {
                    slotService.displaySlot(slot);
                }
            }
        }
    }

    /**
     * Display my bookings.
     *
     * @param userId the user ID
     */
    public void displayMyBookings(int userId) {
        List<Booking> myBookings = viewMyBookings(userId);

        if (myBookings.isEmpty()) {
            System.out.println("You have no bookings yet.");
        } else {
            System.out.println("\n=== MY BOOKINGS ===");
            for (Booking booking : myBookings) {
                if (bookingService != null) {
                    bookingService.displayBooking(booking);

                    // Also show slot details
                    if (slotService != null) {
                        Slot slot = slotService.getSlotById(booking.getSlotId());
                        if (slot != null) {
                            System.out.println("  Time: " + slot.getStartTime() + " - " + slot.getEndTime());
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    /**
     * Display my active bookings only.
     *
     * @param userId the user ID
     */
    public void displayMyActiveBookings(int userId) {
        List<Booking> activeBookings = viewMyActiveBookings(userId);

        if (activeBookings.isEmpty()) {
            System.out.println("You have no active bookings.");
        } else {
            System.out.println("\n=== MY ACTIVE BOOKINGS ===");
            for (Booking booking : activeBookings) {
                if (bookingService != null) {
                    bookingService.displayBooking(booking);

                    // Show slot details
                    if (slotService != null && gymService != null) {
                        Slot slot = slotService.getSlotById(booking.getSlotId());
                        if (slot != null) {
                            System.out.println("  Time: " + slot.getStartTime() + " - " + slot.getEndTime());

                            GymCenter center = gymService.getCenterById(slot.getCenterId());
                            if (center != null) {
                                System.out.println("  Gym: " + center.getCenterName());
                                System.out.println("  Location: " + center.getAddress());
                            }
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    /**
     * Display my waitlist status.
     *
     * @param userId the user ID
     */
    public void displayMyWaitlistStatus(int userId) {
        if (waitlistService == null) {
            System.out.println("❌ WaitlistService not available!");
            return;
        }

        waitlistService.displayUserWaitlistStatus(userId);
    }

    /**
     * Display my statistics.
     *
     * @param userId the user ID
     */
    public void displayMyStatistics(int userId) {
        Map<String, Integer> stats = getMyStatistics(userId);

        // Get customer name
        String customerName = "Customer";
        if (gymUserService != null) {
            try {
                User customer = gymUserService.getUserById(userId);
                if (customer != null) {
                    customerName = customer.getName();
                }
            } catch (UserNotFoundException e) {
                System.out.println("⚠️ User details could not be retrieved for ID: " + userId);
            }
        }

        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║  " + customerName + "'s STATISTICS");
        System.out.println("╠═══════════════════════════════════╣");

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.printf("║ %-25s : %5d ║%n", entry.getKey(), entry.getValue());
        }

        System.out.println("╚═══════════════════════════════════╝");
    }

    /**
     * Display gym with slots.
     *
     * @param centerId the center ID
     */
    public void displayGymWithSlots(int centerId) {
        GymCenter center = getGymCenterDetails(centerId);

        if (center == null) {
            System.out.println("❌ Gym center not found or not approved!");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         GYM CENTER DETAILS             ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("  Name: " + center.getCenterName());
        System.out.println("  Address: " + center.getAddress());
        System.out.println("  City: " + center.getCity());
        System.out.println("  Capacity: " + center.getCapacity());
        System.out.println("╚════════════════════════════════════════╝");

        // Show available slots
        displayAvailableSlotsForGym(centerId);
    }
}