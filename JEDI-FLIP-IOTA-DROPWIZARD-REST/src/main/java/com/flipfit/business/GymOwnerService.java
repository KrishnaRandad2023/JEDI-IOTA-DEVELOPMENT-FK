package com.flipfit.business;

import java.time.LocalTime;
import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.exception.UserNotFoundException;

/**
 * The Class GymOwnerService.
 *
 * @author team IOTA

 */
public class GymOwnerService {

    /** The gym service. */
    // References to other services (GymOwner service uses existing services)
    private GymService gymService;

    /** The slot service. */
    private SlotService slotService;

    /** The booking service. */
    private BookingService bookingService;

    /** The gym user service. */
    private GymUserService gymUserService;

    /**
     * Instantiates a new gym owner service.
     */
    public GymOwnerService() {
        System.out.println("✅ GymOwnerService initialized");
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
     * Sets the gym user service.
     *
     * @param gymUserService the new gym user service
     */
    public void setGymUserService(GymUserService gymUserService) {
        this.gymUserService = gymUserService;
    }

    // ==================== GYM CENTER OPERATIONS ====================

    /**
     * Adds the gym center.
     *
     * @param ownerId    the owner ID
     * @param centerName the center name
     * @param address    the address
     * @param city       the city
     * @param capacity   the capacity
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean addGymCenter(int ownerId, String centerName, String address, String city, int capacity)
            throws UserNotFoundException {
        if (gymService == null) {
            System.out.println("❌ GymService not available!");
            return false;
        }

        // Verify owner exists
        if (gymUserService != null) {
            User owner = gymUserService.getUserById(ownerId);
            if (owner == null) {
                System.out.println("❌ Owner not found!");
                throw new UserNotFoundException("Owner with ID " + ownerId + " not found!");
            }

            if (!(owner instanceof GymOwner)) {
                System.out.println("❌ User is not a gym owner!");
                return false;
            }
        }

        // Create gym center
        GymCenter center = new GymCenter();
        center.setOwnerId(ownerId);
        center.setCenterName(centerName);
        center.setAddress(address);
        center.setCity(city);
        center.setCapacity(capacity);
        center.setApproved(false); // Requires admin approval

        return gymService.addGymCenter(center);
    }

    /**
     * Update gym center.
     *
     * @param ownerId    the owner ID
     * @param centerId   the center ID
     * @param centerName the center name
     * @param address    the address
     * @param city       the city
     * @param capacity   the capacity
     * @return true, if successful
     */
    public boolean updateGymCenter(int ownerId, int centerId, String centerName, String address, String city,
            int capacity) {
        if (gymService == null) {
            System.out.println("❌ GymService not available!");
            return false;
        }

        // Get existing center
        GymCenter center = gymService.getCenterById(centerId);

        if (center == null) {
            System.out.println("❌ Gym center not found!");
            return false;
        }

        // Verify ownership
        if (center.getOwnerId() != ownerId) {
            System.out.println("❌ You can only update your own gym centers!");
            return false;
        }

        // Update center details
        center.setCenterName(centerName);
        center.setAddress(address);
        center.setCity(city);
        center.setCapacity(capacity);

        return gymService.updateGymCenter(center);
    }

    /**
     * Delete gym center.
     *
     * @param ownerId  the owner ID
     * @param centerId the center ID
     * @return true, if successful
     */
    public boolean deleteGymCenter(int ownerId, int centerId) {
        if (gymService == null) {
            System.out.println("❌ GymService not available!");
            return false;
        }

        // Get existing center
        GymCenter center = gymService.getCenterById(centerId);

        if (center == null) {
            System.out.println("❌ Gym center not found!");
            return false;
        }

        // Verify ownership
        if (center.getOwnerId() != ownerId) {
            System.out.println("❌ You can only delete your own gym centers!");
            return false;
        }

        return gymService.deleteGymCenter(centerId);
    }

    /**
     * View my centers.
     *
     * @param ownerId the owner ID
     * @return the list
     */
    public List<GymCenter> viewMyCenters(int ownerId) {
        if (gymService == null) {
            return new ArrayList<>();
        }

        return gymService.getCentersByOwner(ownerId);
    }

    /**
     * View my approved centers.
     *
     * @param ownerId the owner ID
     * @return the list
     */
    public List<GymCenter> viewMyApprovedCenters(int ownerId) {
        List<GymCenter> myCenters = viewMyCenters(ownerId);
        List<GymCenter> approvedCenters = new ArrayList<>();

        for (GymCenter center : myCenters) {
            if (center.isApproved()) {
                approvedCenters.add(center);
            }
        }

        return approvedCenters;
    }

    /**
     * View my pending centers.
     *
     * @param ownerId the owner ID
     * @return the list
     */
    public List<GymCenter> viewMyPendingCenters(int ownerId) {
        List<GymCenter> myCenters = viewMyCenters(ownerId);
        List<GymCenter> pendingCenters = new ArrayList<>();

        for (GymCenter center : myCenters) {
            if (!center.isApproved()) {
                pendingCenters.add(center);
            }
        }

        return pendingCenters;
    }

    // ==================== SLOT OPERATIONS ====================

    /**
     * Adds the slot to center.
     *
     * @param ownerId    the owner ID
     * @param centerId   the center ID
     * @param startTime  the start time
     * @param endTime    the end time
     * @param totalSeats the total seats
     * @return true, if successful
     */
    public boolean addSlotToCenter(int ownerId, int centerId, LocalTime startTime, LocalTime endTime, int totalSeats) {
        if (gymService == null || slotService == null) {
            System.out.println("❌ Required services not available!");
            return false;
        }

        // Verify center ownership
        GymCenter center = gymService.getCenterById(centerId);

        if (center == null) {
            System.out.println("❌ Gym center not found!");
            return false;
        }

        if (center.getOwnerId() != ownerId) {
            System.out.println("❌ You can only add slots to your own gym centers!");
            return false;
        }

        if (!center.isApproved()) {
            System.out.println("❌ Center must be approved before adding slots!");
            return false;
        }

        // Add slot
        return slotService.addSlot(centerId, startTime, endTime, totalSeats);
    }

    /**
     * Update slot.
     *
     * @param ownerId the owner ID
     * @param slot    the slot
     * @return true, if successful
     */
    public boolean updateSlot(int ownerId, Slot slot) {
        if (gymService == null || slotService == null) {
            System.out.println("❌ Required services not available!");
            return false;
        }

        // Verify center ownership
        GymCenter center = gymService.getCenterById(slot.getCenterId());

        if (center == null) {
            System.out.println("❌ Gym center not found!");
            return false;
        }

        if (center.getOwnerId() != ownerId) {
            System.out.println("❌ You can only update slots for your own gym centers!");
            return false;
        }

        return slotService.updateSlot(slot);
    }

    /**
     * Delete slot.
     *
     * @param ownerId the owner ID
     * @param slotId  the slot ID
     * @return true, if successful
     */
    public boolean deleteSlot(int ownerId, int slotId) {
        if (gymService == null || slotService == null) {
            System.out.println("❌ Required services not available!");
            return false;
        }

        // Get slot
        Slot slot = slotService.getSlotById(slotId);

        if (slot == null) {
            System.out.println("❌ Slot not found!");
            return false;
        }

        // Verify center ownership
        GymCenter center = gymService.getCenterById(slot.getCenterId());

        if (center == null || center.getOwnerId() != ownerId) {
            System.out.println("❌ You can only delete slots for your own gym centers!");
            return false;
        }

        return slotService.deleteSlot(slotId);
    }

    /**
     * View my slots.
     *
     * @param ownerId the owner ID
     * @return the list
     */
    public List<Slot> viewMySlots(int ownerId) {
        if (slotService == null) {
            return new ArrayList<>();
        }

        List<GymCenter> myCenters = viewMyApprovedCenters(ownerId);
        List<Slot> mySlots = new ArrayList<>();

        for (GymCenter center : myCenters) {
            List<Slot> centerSlots = slotService.getSlotsByCenter(center.getCenterId());
            mySlots.addAll(centerSlots);
        }

        return mySlots;
    }

    /**
     * View slots for center.
     *
     * @param ownerId  the owner ID
     * @param centerId the center ID
     * @return the list
     */
    public List<Slot> viewSlotsForCenter(int ownerId, int centerId) {
        if (gymService == null || slotService == null) {
            return new ArrayList<>();
        }

        // Verify ownership
        GymCenter center = gymService.getCenterById(centerId);

        if (center == null || center.getOwnerId() != ownerId) {
            System.out.println("❌ You can only view slots for your own centers!");
            return new ArrayList<>();
        }

        return slotService.getSlotsByCenter(centerId);
    }

    // ==================== BOOKING & REVENUE OPERATIONS ====================

    /**
     * View bookings for my centers.
     *
     * @param ownerId the owner ID
     * @return the list
     */
    public List<Booking> viewBookingsForMyCenters(int ownerId) {
        if (bookingService == null || slotService == null) {
            return new ArrayList<>();
        }

        List<Slot> mySlots = viewMySlots(ownerId);
        List<Booking> myBookings = new ArrayList<>();

        for (Slot slot : mySlots) {
            List<Booking> slotBookings = bookingService.getBookingsBySlot(slot.getSlotId(), null);
            myBookings.addAll(slotBookings);
        }

        return myBookings;
    }

    /**
     * View bookings for center.
     *
     * @param ownerId  the owner ID
     * @param centerId the center ID
     * @return the list
     */
    public List<Booking> viewBookingsForCenter(int ownerId, int centerId) {
        if (gymService == null || slotService == null || bookingService == null) {
            return new ArrayList<>();
        }

        // Verify ownership
        GymCenter center = gymService.getCenterById(centerId);

        if (center == null || center.getOwnerId() != ownerId) {
            System.out.println("❌ You can only view bookings for your own centers!");
            return new ArrayList<>();
        }

        List<Slot> centerSlots = slotService.getSlotsByCenter(centerId);
        List<Booking> centerBookings = new ArrayList<>();

        for (Slot slot : centerSlots) {
            List<Booking> slotBookings = bookingService.getBookingsBySlot(slot.getSlotId(), null);
            centerBookings.addAll(slotBookings);
        }

        return centerBookings;
    }

    /**
     * Gets the my statistics.
     *
     * @param ownerId the owner ID
     * @return the my statistics
     */
    public Map<String, Integer> getMyStatistics(int ownerId) {
        Map<String, Integer> stats = new HashMap<>();

        List<GymCenter> myCenters = viewMyCenters(ownerId);
        List<GymCenter> approvedCenters = viewMyApprovedCenters(ownerId);
        List<Slot> mySlots = viewMySlots(ownerId);
        List<Booking> myBookings = viewBookingsForMyCenters(ownerId);

        // Count confirmed bookings
        int confirmedBookings = 0;
        for (Booking booking : myBookings) {
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                confirmedBookings++;
            }
        }

        stats.put("Total Centers", myCenters.size());
        stats.put("Approved Centers", approvedCenters.size());
        stats.put("Pending Centers", myCenters.size() - approvedCenters.size());
        stats.put("Total Slots", mySlots.size());
        stats.put("Total Bookings", myBookings.size());
        stats.put("Confirmed Bookings", confirmedBookings);

        return stats;
    }

    // ==================== DISPLAY METHODS ====================

    /**
     * Display my centers.
     *
     * @param ownerId the owner ID
     */
    public void displayMyCenters(int ownerId) {
        List<GymCenter> myCenters = viewMyCenters(ownerId);

        if (myCenters.isEmpty()) {
            System.out.println("You have no gym centers registered yet.");
        } else {
            System.out.println("\n=== MY GYM CENTERS ===");
            for (GymCenter center : myCenters) {
                if (gymService != null) {
                    gymService.displayCenter(center);
                }
            }
        }
    }

    /**
     * Display my slots.
     *
     * @param ownerId the owner ID
     */
    public void displayMySlots(int ownerId) {
        List<Slot> mySlots = viewMySlots(ownerId);

        if (mySlots.isEmpty()) {
            System.out.println("No slots found for your centers.");
        } else {
            System.out.println("\n=== MY SLOTS ===");
            for (Slot slot : mySlots) {
                if (slotService != null) {
                    slotService.displaySlot(slot);
                }
            }
        }
    }

    /**
     * Display my statistics.
     *
     * @param ownerId the owner ID
     */
    public void displayMyStatistics(int ownerId) {
        Map<String, Integer> stats = getMyStatistics(ownerId);

        // Get owner name
        String ownerName = "Gym Owner";
        if (gymUserService != null) {
            try {
                User owner = gymUserService.getUserById(ownerId);
                if (owner != null) {
                    ownerName = owner.getName();
                }
            } catch (UserNotFoundException e) {
                System.out.println("⚠️ User details could not be retrieved for ID: " + ownerId);
            }
        }

        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║  " + ownerName + "'s STATISTICS");
        System.out.println("╠═══════════════════════════════════╣");

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.printf("║ %-25s : %5d ║%n", entry.getKey(), entry.getValue());
        }

        System.out.println("╚═══════════════════════════════════╝");
    }

    /**
     * Display bookings for center.
     *
     * @param ownerId  the owner ID
     * @param centerId the center ID
     */
    public void displayBookingsForCenter(int ownerId, int centerId) {
        List<Booking> centerBookings = viewBookingsForCenter(ownerId, centerId);

        if (centerBookings.isEmpty()) {
            System.out.println("No bookings found for this center.");
        } else {
            System.out.println("\n=== BOOKINGS FOR CENTER ID: " + centerId + " ===");
            for (Booking booking : centerBookings) {
                if (bookingService != null) {
                    bookingService.displayBooking(booking);
                }
            }
        }
    }
}