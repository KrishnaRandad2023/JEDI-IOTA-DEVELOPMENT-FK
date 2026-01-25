package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;

public class BookingService implements BookingServiceInterface {
    // Collections
    private Map<Integer, Booking> bookings;                      // bookingId -> Booking
    private Map<Integer, List<Booking>> userBookings;            // userId -> List of Bookings
    private Map<Integer, List<Booking>> slotBookings;            // slotId -> List of Bookings
    private Map<Integer, Integer> slotBookingCount;              // slotId -> count of confirmed bookings
    private int bookingIdCounter;
    
    // Reference to SlotService for seat management
    private SlotService slotService;
    private WaitlistService waitlistService;
    
    // Constructor with hard-coded data
    public BookingService() {
        this.bookings = new HashMap<>();
        this.userBookings = new HashMap<>();
        this.slotBookings = new HashMap<>();
        this.slotBookingCount = new HashMap<>();
        this.bookingIdCounter = 1;
        
        System.out.println("✅ BookingService initialized");
    }
    
    // Set dependencies (to be called after all services are created)
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }
    
    public void setWaitlistService(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }
    
    // Initialize with some hard-coded bookings
    public void initializeHardcodedBookings() {
        // Customer 1 (Amit - userId=4) bookings
        createBookingInternal(4, 1, BookingStatus.CONFIRMED);  // Slot 1 - Bellandur 6-7 AM
        createBookingInternal(4, 3, BookingStatus.CONFIRMED);  // Slot 3 - Bellandur 6-7 PM
        
        // Customer 2 (Sneha - userId=5) bookings
        createBookingInternal(5, 2, BookingStatus.CONFIRMED);  // Slot 2 - Bellandur 7-8 AM
        createBookingInternal(5, 6, BookingStatus.CONFIRMED);  // Slot 6 - HSR 5-6 PM
        
        // Customer 3 (Vikram - userId=6) bookings
        createBookingInternal(6, 9, BookingStatus.CONFIRMED);  // Slot 9 - Indiranagar 6-7 AM
        
        System.out.println("✅ Initialized with " + bookings.size() + " bookings");
    }
    
    // Helper method to create bookings during initialization
    private void createBookingInternal(int userId, int slotId, BookingStatus status) {
        Booking booking = new Booking();
        booking.setBookingId(bookingIdCounter++);
        booking.setUserId(userId);
        booking.setSlotId(slotId);
        booking.setBookingDate(new Date());
        booking.setStatus(status);
        
        // Add to collections
        bookings.put(booking.getBookingId(), booking);
        userBookings.computeIfAbsent(userId, k -> new ArrayList<>()).add(booking);
        slotBookings.computeIfAbsent(slotId, k -> new ArrayList<>()).add(booking);
        
        // Update slot booking count
        if (status == BookingStatus.CONFIRMED) {
            slotBookingCount.put(slotId, slotBookingCount.getOrDefault(slotId, 0) + 1);
            
            // Decrease available seats if slotService is available
            if (slotService != null) {
                slotService.decreaseAvailableSeats(slotId);
            }
        }
    }
    
    // 1. Check availability of a slot
    @Override
    public boolean checkAvailability(int slotId, Date date) {
        // Check if slot has available seats
        if (slotService != null) {
            return slotService.hasAvailableSeats(slotId);
        }
        
        // Fallback: just check if slot exists
        return slotBookings.getOrDefault(slotId, new ArrayList<>()).size() < 20;
    }
    
    // 2. Book a slot for user
    @Override
    public void bookSlot(int userId, int slotId, Date date) {
        // Check if slot has available seats
        if (slotService != null && !slotService.hasAvailableSeats(slotId)) {
            System.out.println("❌ Slot is full! Adding to waitlist...");
            
            // Add to waitlist
            if (waitlistService != null) {
                waitlistService.addToWaitlist(userId, slotId);
            }
            return;
        }
        
        // Create new booking
        Booking booking = new Booking();
        booking.setBookingId(bookingIdCounter++);
        booking.setUserId(userId);
        booking.setSlotId(slotId);
        booking.setBookingDate(date != null ? date : new Date());
        booking.setStatus(BookingStatus.CONFIRMED);
        
        // Add to collections
        bookings.put(booking.getBookingId(), booking);
        userBookings.computeIfAbsent(userId, k -> new ArrayList<>()).add(booking);
        slotBookings.computeIfAbsent(slotId, k -> new ArrayList<>()).add(booking);
        
        // Update slot booking count
        slotBookingCount.put(slotId, slotBookingCount.getOrDefault(slotId, 0) + 1);
        
        // Decrease available seats
        if (slotService != null) {
            slotService.decreaseAvailableSeats(slotId);
        }
        
        System.out.println("✅ Booking successful!");
        System.out.println("   Booking ID: " + booking.getBookingId());
        System.out.println("   Slot ID: " + slotId);
        System.out.println("   Date: " + booking.getBookingDate());
    }
    
    // 3. Cancel a booking
    @Override
    public void cancelBooking(int bookingId) {
        Booking booking = bookings.get(bookingId);
        
        if (booking == null) {
            System.out.println("❌ Booking not found!");
            return;
        }
        
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("⚠️ Booking already cancelled!");
            return;
        }
        
        // Update booking status
        booking.setStatus(BookingStatus.CANCELLED);
        
        // Decrease slot booking count
        int slotId = booking.getSlotId();
        slotBookingCount.put(slotId, slotBookingCount.getOrDefault(slotId, 1) - 1);
        
        // Increase available seats
        if (slotService != null) {
            slotService.increaseAvailableSeats(slotId);
        }
        
        // Promote from waitlist if applicable
        if (waitlistService != null) {
            waitlistService.promoteFromWaitlist(slotId);
        }
        
        System.out.println("✅ Booking cancelled successfully!");
        System.out.println("   Booking ID: " + bookingId);
    }
    
    // 4. Get all bookings for a specific user
    public List<Booking> getBookingsByUser(int userId) {
        return userBookings.getOrDefault(userId, new ArrayList<>());
    }
    
    // 5. Get active bookings for a user (only CONFIRMED)
    public List<Booking> getActiveBookingsByUser(int userId) {
        List<Booking> userBookingList = userBookings.getOrDefault(userId, new ArrayList<>());
        List<Booking> activeBookings = new ArrayList<>();
        
        for (Booking booking : userBookingList) {
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                activeBookings.add(booking);
            }
        }
        
        return activeBookings;
    }
    
    // 6. Get all bookings for a specific slot on a date
    public List<Booking> getBookingsBySlot(int slotId, Date date) {
        // For simplicity, return all bookings for the slot
        // In real scenario, would filter by date
        return slotBookings.getOrDefault(slotId, new ArrayList<>());
    }
    
    // 7. Get booking by ID
    public Booking getBookingById(int bookingId) {
        return bookings.get(bookingId);
    }
    
    // 8. Get all bookings (Admin view)
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }
    
    // 9. Get bookings by status
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        List<Booking> filteredBookings = new ArrayList<>();
        
        for (Booking booking : bookings.values()) {
            if (booking.getStatus() == status) {
                filteredBookings.add(booking);
            }
        }
        
        return filteredBookings;
    }
    
    // 10. Get booking count for a slot
    public int getBookingCountForSlot(int slotId) {
        return slotBookingCount.getOrDefault(slotId, 0);
    }
    
    // 11. Check if user has already booked a slot
    public boolean hasUserBookedSlot(int userId, int slotId) {
        List<Booking> userBookingList = userBookings.getOrDefault(userId, new ArrayList<>());
        
        for (Booking booking : userBookingList) {
            if (booking.getSlotId() == slotId && booking.getStatus() == BookingStatus.CONFIRMED) {
                return true;
            }
        }
        
        return false;
    }
    
    // 12. Update booking status
    public boolean updateBookingStatus(int bookingId, BookingStatus newStatus) {
        Booking booking = bookings.get(bookingId);
        
        if (booking == null) {
            System.out.println("❌ Booking not found!");
            return false;
        }
        
        BookingStatus oldStatus = booking.getStatus();
        booking.setStatus(newStatus);
        
        // Update slot counts if status changed from/to CONFIRMED
        if (oldStatus == BookingStatus.CONFIRMED && newStatus != BookingStatus.CONFIRMED) {
            slotBookingCount.put(booking.getSlotId(), 
                slotBookingCount.getOrDefault(booking.getSlotId(), 1) - 1);
            
            if (slotService != null) {
                slotService.increaseAvailableSeats(booking.getSlotId());
            }
        } else if (oldStatus != BookingStatus.CONFIRMED && newStatus == BookingStatus.CONFIRMED) {
            slotBookingCount.put(booking.getSlotId(), 
                slotBookingCount.getOrDefault(booking.getSlotId(), 0) + 1);
            
            if (slotService != null) {
                slotService.decreaseAvailableSeats(booking.getSlotId());
            }
        }
        
        System.out.println("✅ Booking status updated to: " + newStatus);
        return true;
    }
    
    // 13. Delete booking (Admin functionality)
    public boolean deleteBooking(int bookingId) {
        Booking booking = bookings.remove(bookingId);
        
        if (booking != null) {
            // Remove from user's bookings
            List<Booking> userBookingList = userBookings.get(booking.getUserId());
            if (userBookingList != null) {
                userBookingList.removeIf(b -> b.getBookingId() == bookingId);
            }
            
            // Remove from slot's bookings
            List<Booking> slotBookingList = slotBookings.get(booking.getSlotId());
            if (slotBookingList != null) {
                slotBookingList.removeIf(b -> b.getBookingId() == bookingId);
            }
            
            // Update counts if booking was confirmed
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                slotBookingCount.put(booking.getSlotId(), 
                    slotBookingCount.getOrDefault(booking.getSlotId(), 1) - 1);
                
                if (slotService != null) {
                    slotService.increaseAvailableSeats(booking.getSlotId());
                }
            }
            
            System.out.println("✅ Booking deleted!");
            return true;
        }
        
        System.out.println("❌ Booking not found!");
        return false;
    }
    
    // 14. Display booking details (helper method)
    public void displayBooking(Booking booking) {
        if (booking != null) {
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("User ID: " + booking.getUserId());
            System.out.println("Slot ID: " + booking.getSlotId());
            System.out.println("Date: " + booking.getBookingDate());
            System.out.println("Status: " + booking.getStatus());
            System.out.println("---");
        }
    }
    
    // 15. Display all bookings for a user
    public void displayUserBookings(int userId) {
        List<Booking> userBookingList = getBookingsByUser(userId);
        
        if (userBookingList.isEmpty()) {
            System.out.println("No bookings found for user ID: " + userId);
        } else {
            System.out.println("\n=== Bookings for User ID: " + userId + " ===");
            for (Booking booking : userBookingList) {
                displayBooking(booking);
            }
        }
    }
    
    // 16. Display all bookings
    public void displayAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings in the system!");
        } else {
            System.out.println("\n=== ALL BOOKINGS ===");
            for (Booking booking : bookings.values()) {
                displayBooking(booking);
            }
        }
    }
}