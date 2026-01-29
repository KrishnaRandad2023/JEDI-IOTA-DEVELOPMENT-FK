package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.BookingNotDoneException;

/**
 * Service class that orchestrates operations for Gym Customers.
 * Acts as a facade, delegating to specialized services like {@link GymService},
 * {@link SlotService}, {@link BookingService}, and {@link WaitlistService}.
 * 
 * @author team IOTA
 */
public class CustomerService {

    private GymService gymService;
    private SlotService slotService;
    private BookingService bookingService;
    private WaitlistService waitlistService;
    private GymUserService gymUserService;

    /**
     * Instantiates a new customer service.
     */
    public CustomerService() {
        System.out.println("✅ CustomerService initialized");
    }

    /**
     * Sets the gym service dependency.
     *
     * @param gymService the gym service to use
     */
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    /**
     * Sets the slot service dependency.
     *
     * @param slotService the slot service to use
     */
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }

    /**
     * Sets the booking service dependency.
     *
     * @param bookingService the booking service to use
     */
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Sets the waitlist service dependency.
     *
     * @param waitlistService the waitlist service to use
     */
    public void setWaitlistService(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }

    /**
     * Sets the gym user service dependency.
     *
     * @param gymUserService the gym user service to use
     */
    public void setGymUserService(GymUserService gymUserService) {
        this.gymUserService = gymUserService;
    }

    // ==================== GYM BROWSING OPERATIONS ====================

    /**
     * Retrieves all approved gym centers in a specific city.
     *
     * @param city the name of the city
     * @return a list of approved gym centers
     */
    public List<GymCenter> viewGymsInCity(String city) {
        if (gymService == null) {
            System.out.println("❌ GymService not available!");
            return new ArrayList<>();
        }

        return gymService.viewAllCenters(city);
    }

    /**
     * Retrieves all approved gym centers in the entire system.
     *
     * @return a list of approved gym centers
     */
    public List<GymCenter> viewAllGyms() {
        if (gymService == null) {
            return new ArrayList<>();
        }

        return gymService.getApprovedCenters();
    }

    /**
     * Retrieves details of a specific gym center by its ID, provided it is
     * approved.
     *
     * @param centerId the unique ID of the gym center
     * @return the GymCenter object, or null if not found or not approved
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
     * Searches for approved gym centers whose names contain the given search term
     * (case-insensitive).
     *
     * @param searchTerm the term to search for
     * @return a list of matching gym centers
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
     * Retrieves all available time slots for a specific gym center.
     *
     * @param centerId the unique ID of the gym center
     * @return a list of slots for the gym
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
     * Retrieves only those slots for a specific gym center that have at least
     * one available seat.
     *
     * @param centerId the unique ID of the gym center
     * @return a list of available slots
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
     * Retrieves details of a specific slot by its ID.
     *
     * @param slotId the unique ID of the slot
     * @return the Slot object
     */
    public Slot getSlotDetails(int slotId) {
        if (slotService == null) {
            return null;
        }

        return slotService.getSlotById(slotId);
    }

    // ==================== BOOKING OPERATIONS ====================

    /**
     * Attempts to book a slot for a customer.
     *
     * @param userId the ID of the customer booking the slot
     * @param slotId the ID of the slot to book
     * @return the booking ID if successful, -1 if added to waitlist, or 0 if
     *         booking failed
     * @throws UserNotFoundException     if the user ID does not exist in the system
     * @throws SlotNotAvailableException if the slot is full and waitlist is also
     *                                   full
     */
    public int bookSlot(int userId, int slotId) throws UserNotFoundException, SlotNotAvailableException {
        if (bookingService == null || slotService == null) {
            System.out.println("❌ Required services not available!");
            return 0;
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
                return 0;
            }
        }

        // Check if slot exists
        Slot slot = slotService.getSlotById(slotId);
        if (slot == null) {
            System.out.println("❌ Slot not found!");
            return 0;
        }

        // Check if user already booked this slot
        if (bookingService.hasUserBookedSlot(userId, slotId)) {
            System.out.println("❌ You have already booked this slot!");
            return 0;
        }

        // Try to book and return id/-1/0
        return bookingService.bookSlot(userId, slotId, new Date());
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
     * Retrieves all bookings (confirmed and cancelled) for the customer.
     *
     * @param userId the unique ID of the customer
     * @return a list of all bookings
     */
    public List<Booking> viewMyBookings(int userId) {
        if (bookingService == null) {
            return new ArrayList<>();
        }

        return bookingService.getBookingsByUser(userId);
    }

    /**
     * Retrieves only the confirmed/active bookings for the customer.
     *
     * @param userId the unique ID of the customer
     * @return a list of active bookings
     */
    public List<Booking> viewMyActiveBookings(int userId) {
        if (bookingService == null) {
            return new ArrayList<>();
        }

        return bookingService.getActiveBookingsByUser(userId);
    }

    /**
     * Retrieves the booking history for the customer, including completed and
     * cancelled bookings.
     *
     * @param userId the unique ID of the customer
     * @return a list of historical bookings
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
     * Retrieves all waitlist entries for the customer.
     *
     * @param userId the unique ID of the customer
     * @return a list of waitlist entries
     */
    public List<Waitlist> viewMyWaitlistStatus(int userId) {
        if (waitlistService == null) {
            return new ArrayList<>();
        }

        return waitlistService.getWaitlistByUser(userId);
    }

    /**
     * Gets the customer's priority position in the waitlist for a specific slot.
     *
     * @param userId the unique ID of the customer
     * @param slotId the unique ID of the slot
     * @return the position (1-based), or -1 if not found
     */
    public int getMyWaitlistPosition(int userId, int slotId) {
        if (waitlistService == null) {
            return -1;
        }

        return waitlistService.getWaitlistPosition(userId, slotId);
    }

    /**
     * Removes the customer from a waitlist.
     *
     * @param userId     the unique ID of the customer
     * @param waitlistId the ID of the waitlist entry
     * @return true if removal was successful
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
     * Updates the customer's profile information.
     *
     * @param user the GymCustomer object with updated details
     * @return true if update was successful
     * @throws UserNotFoundException if user doesn't exist
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
     * Changes the customer's password.
     *
     * @param userId      the unique ID of the customer
     * @param oldPassword the current raw password
     * @param newPassword the new raw password
     * @return true if password change was successful
     * @throws UserNotFoundException if user doesn't exist
     */
    public boolean changeMyPassword(int userId, String oldPassword, String newPassword) throws UserNotFoundException {
        if (gymUserService == null) {
            System.out.println("❌ GymUserService not available!");
            return false;
        }

        return gymUserService.changePassword(userId, oldPassword, newPassword);
    }

    /**
     * Calculates and returns various booking and waitlist statistics for the
     * customer.
     *
     * @param userId the unique ID of the customer
     * @return a map containing statistic categories and their counts
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
                case CONFIRMED:
                    confirmedCount++;
                    break;
                case CANCELLED:
                    cancelledCount++;
                    break;
                case COMPLETED:
                    completedCount++;
                    break;
                default:
                    break;
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
     * Displays a list of gyms in a city to the console.
     *
     * @param city the name of the city
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
     * Displays all available slots for a gym to the console.
     *
     * @param centerId the unique ID of the gym center
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
     * Displays all bookings for the customer to the console.
     *
     * @param userId the unique ID of the customer
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
     * Displays only confirmed/active bookings for the customer to the console.
     *
     * @param userId the unique ID of the customer
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
     * Displays the customer's current waitlist status for all their entries to the
     * console.
     *
     * @param userId the unique ID of the customer
     */
    public void displayMyWaitlistStatus(int userId) {
        if (waitlistService == null) {
            System.out.println("❌ WaitlistService not available!");
            return;
        }

        waitlistService.displayUserWaitlistStatus(userId);
    }

    /**
     * Displays a formatted summary of the customer's statistics to the console.
     *
     * @param userId the unique ID of the customer
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
     * Displays gym center details and its available slots to the console.
     *
     * @param centerId the unique ID of the gym center
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