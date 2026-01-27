package com.flipfit.business;

import java.time.LocalTime;
import java.util.*;
import com.flipfit.bean.*;

public class GymOwnerService {
    // References to other services (GymOwner service uses existing services)
    private GymService gymService;
    private SlotService slotService;
    private BookingService bookingService;
    private GymUserService gymUserService;
    
    // Constructor
    public GymOwnerService() {
        System.out.println("✅ GymOwnerService initialized");
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
    
    public void setGymUserService(GymUserService gymUserService) {
        this.gymUserService = gymUserService;
    }
    
    // ==================== GYM CENTER OPERATIONS ====================
    
    // 1. Add new gym center (requires admin approval)
    public boolean addGymCenter(int ownerId, String centerName, String address, String city, int capacity) {
        if (gymService == null) {
            System.out.println("❌ GymService not available!");
            return false;
        }
        
        // Verify owner exists
        if (gymUserService != null) {
            User owner = gymUserService.getUserById(ownerId);
            if (owner == null) {
                System.out.println("❌ Owner not found!");
                return false;
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
        center.setApproved(false);  // Requires admin approval
        
        return gymService.addGymCenter(center);
    }
    
    // 2. Update gym center (only owner's own centers)
    public boolean updateGymCenter(int ownerId, int centerId, String centerName, String address, String city, int capacity) {
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
    
    // 3. Delete gym center (only owner's own centers)
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
    
    // 4. View all centers owned by this owner
    public List<GymCenter> viewMyCenters(int ownerId) {
        if (gymService == null) {
            return new ArrayList<>();
        }
        
        return gymService.getCentersByOwner(ownerId);
    }
    
    // 5. View approved centers only
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
    
    // 6. View pending centers
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
    
    // 7. Add slot to a gym center (only to owner's centers)
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
    
    // 8. Update slot (only for owner's centers)
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
    
    // 9. Delete slot (only for owner's centers)
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
    
    // 10. View all slots for owner's centers
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
    
    // 11. View slots for a specific center
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
    
    // 12. View all bookings for owner's centers
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
    
    // 13. View bookings for a specific center
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
    
    // 14. Get statistics for owner's centers
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
    
    // 15. Display owner's centers
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
    
    // 16. Display owner's slots
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
    
    // 17. Display statistics
    public void displayMyStatistics(int ownerId) {
        Map<String, Integer> stats = getMyStatistics(ownerId);
        
        // Get owner name
        String ownerName = "Gym Owner";
        if (gymUserService != null) {
            User owner = gymUserService.getUserById(ownerId);
            if (owner != null) {
                ownerName = owner.getName();
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
    
    // 18. Display bookings for a specific center
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