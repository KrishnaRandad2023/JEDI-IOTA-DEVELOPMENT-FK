package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;

public class CustomerService {
    // References to other services (Customer service uses existing services)
    private GymService gymService;
    private SlotService slotService;
    private BookingService bookingService;
    private WaitlistService waitlistService;
    private GymUserService gymUserService;
    
    // Constructor
    public CustomerService() {
        System.out.println("✅ CustomerService initialized");
    }
    
    // Set service dependencies
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }
    
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }
    
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    public void setWaitlistService(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }
    
    public void setGymUserService(GymUserService gymUserService) {
        this.gymUserService = gymUserService;
    }
    
    // ==================== GYM BROWSING OPERATIONS ====================
    
    // 1. View all approved gyms in a specific city
    public List<GymCenter> viewGymsInCity(String city) {
        if (gymService == null) {
            System.out.println("❌ GymService not available!");
            return new ArrayList<>();
        }
        
        return gymService.viewAllCenters(city);
    }
    
    // 2. View all approved gyms (all cities)
    public List<GymCenter> viewAllGyms() {
        if (gymService == null) {
            return new ArrayList<>();
        }
        
        return gymService.getApprovedCenters();
    }
    
    // 3. Get gym center details by ID
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
    
    // 4. Search gyms by name
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
    
    // 5. View all slots for a specific gym center
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
    
    // 6. View only available slots for a gym center (slots with available seats)
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
    
    // 7. Get slot details
    public Slot getSlotDetails(int slotId) {
        if (slotService == null) {
            return null;
        }
        
        return slotService.getSlotById(slotId);
    }
    
    // ==================== BOOKING OPERATIONS ====================
    
    // 8. Book a slot
    public boolean bookSlot(int userId, int slotId) {
        if (bookingService == null || slotService == null) {
            System.out.println("❌ Required services not available!");
            return false;
        }
        
        // Verify user is a customer
        if (gymUserService != null) {
            User user = gymUserService.getUserById(userId);
            if (user == null) {
                System.out.println("❌ User not found!");
                return false;
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
        
        // Try to book
        bookingService.bookSlot(userId, slotId, new Date());
        return true;
    }
    
    // 9. Cancel a booking
    public boolean cancelBooking(int userId, int bookingId) {
        if (bookingService == null) {
            System.out.println("❌ BookingService not available!");
            return false;
        }
        
        // Get booking
        Booking booking = bookingService.getBookingById(bookingId);
        
        if (booking == null) {
            System.out.println("❌ Booking not found!");
            return false;
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
    
    // 10. View my bookings (all statuses)
    public List<Booking> viewMyBookings(int userId) {
        if (bookingService == null) {
            return new ArrayList<>();
        }
        
        return bookingService.getBookingsByUser(userId);
    }
    
    // 11. View my active bookings (only CONFIRMED)
    public List<Booking> viewMyActiveBookings(int userId) {
        if (bookingService == null) {
            return new ArrayList<>();
        }
        
        return bookingService.getActiveBookingsByUser(userId);
    }
    
    // 12. View booking history (CANCELLED, COMPLETED)
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
    
    // 13. View my waitlist status (all slots I'm waiting for)
    public List<Waitlist> viewMyWaitlistStatus(int userId) {
        if (waitlistService == null) {
            return new ArrayList<>();
        }
        
        return waitlistService.getWaitlistByUser(userId);
    }
    
    // 14. Get my position in waitlist for a specific slot
    public int getMyWaitlistPosition(int userId, int slotId) {
        if (waitlistService == null) {
            return -1;
        }
        
        return waitlistService.getWaitlistPosition(userId, slotId);
    }
    
    // 15. Remove myself from waitlist
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
    
    // 16. Update my profile
    public boolean updateMyProfile(User user) {
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
    
    // 17. Change my password
    public boolean changeMyPassword(int userId, String oldPassword, String newPassword) {
        if (gymUserService == null) {
            System.out.println("❌ GymUserService not available!");
            return false;
        }
        
        return gymUserService.changePassword(userId, oldPassword, newPassword);
    }
    
    // 18. Get my statistics
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
    
    // 19. Display gyms in a city
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
    
    // 20. Display available slots for a gym
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
    
    // 21. Display my bookings
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
    
    // 22. Display my active bookings only
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
    
    // 23. Display my waitlist status
    public void displayMyWaitlistStatus(int userId) {
        if (waitlistService == null) {
            System.out.println("❌ WaitlistService not available!");
            return;
        }
        
        waitlistService.displayUserWaitlistStatus(userId);
    }
    
    // 24. Display my statistics
    public void displayMyStatistics(int userId) {
        Map<String, Integer> stats = getMyStatistics(userId);
        
        // Get customer name
        String customerName = "Customer";
        if (gymUserService != null) {
            User customer = gymUserService.getUserById(userId);
            if (customer != null) {
                customerName = customer.getName();
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
    
    // 25. Display gym details with available slots
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