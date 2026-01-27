package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.Waitlist;

public class WaitlistService implements WaitlistServiceInterface {
    // Collections - Using Queue for FIFO behavior
    private Map<Integer, Queue<Waitlist>> slotWaitlists;    // slotId -> Queue of Waitlist entries
    private Map<Integer, Waitlist> waitlistEntries;         // waitlistId -> Waitlist entry
    private int waitlistIdCounter;
    
    // Reference to BookingService for promotion
    private BookingService bookingService;
    
    // Constructor
    public WaitlistService() {
        this.slotWaitlists = new HashMap<>();
        this.waitlistEntries = new HashMap<>();
        this.waitlistIdCounter = 1;
        
        System.out.println("✅ WaitlistService initialized");
    }
    
    // Set dependency (to be called after BookingService is created)
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    // Initialize with some hard-coded waitlist entries
    public void initializeHardcodedWaitlist() {
        // Simulate a popular slot with waitlist
        // Slot 3 (Bellandur 6-7 PM) - 2 people waiting
        addToWaitlist(5, 3);  // Sneha waiting for slot 3
        addToWaitlist(6, 3);  // Vikram waiting for slot 3
        
        System.out.println("✅ Initialized with " + waitlistEntries.size() + " waitlist entries");
    }
    
    // 1. Add user to waitlist for a slot
    @Override
    public boolean addToWaitlist(int userId, int slotId) {
        // Check if user is already in waitlist for this slot
        if (isUserInWaitlist(userId, slotId)) {
            System.out.println("⚠️ User already in waitlist for this slot!");
            return false;
        }
        
        // Get or create queue for this slot
        Queue<Waitlist> queue = slotWaitlists.computeIfAbsent(slotId, k -> new LinkedList<>());
        
        // Create waitlist entry
        Waitlist entry = new Waitlist();
        entry.setWaitlistId(waitlistIdCounter++);
        entry.setUserId(userId);
        entry.setSlotId(slotId);
        entry.setRequestDate(new Date());
        entry.setPriorityPosition(queue.size() + 1);  // Position in queue
        
        // Add to queue (FIFO - First In First Out)
        queue.offer(entry);
        waitlistEntries.put(entry.getWaitlistId(), entry);
        
        System.out.println("✅ Added to waitlist!");
        System.out.println("   Waitlist ID: " + entry.getWaitlistId());
        System.out.println("   Position: " + entry.getPriorityPosition());
        
        return true;
    }
    
    // 2. Remove user from waitlist
    @Override
    public boolean removeFromWaitlist(int waitlistId) {
        Waitlist entry = waitlistEntries.remove(waitlistId);
        
        if (entry == null) {
            System.out.println("❌ Waitlist entry not found!");
            return false;
        }
        
        // Remove from queue
        Queue<Waitlist> queue = slotWaitlists.get(entry.getSlotId());
        if (queue != null) {
            queue.removeIf(w -> w.getWaitlistId() == waitlistId);
            
            // Update priority positions for remaining entries
            updatePriorityPositions(entry.getSlotId());
        }
        
        System.out.println("✅ Removed from waitlist!");
        return true;
    }
    
    // 3. Get all waitlist entries for a specific slot
    @Override
    public List<Waitlist> getWaitlistBySlot(int slotId) {
        Queue<Waitlist> queue = slotWaitlists.get(slotId);
        
        if (queue == null || queue.isEmpty()) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(queue);
    }
    
    // 4. Get user's position in waitlist
    @Override
    public int getWaitlistPosition(int userId, int slotId) {
        Queue<Waitlist> queue = slotWaitlists.get(slotId);
        
        if (queue == null) {
            return -1;
        }
        
        int position = 1;
        for (Waitlist entry : queue) {
            if (entry.getUserId() == userId) {
                return position;
            }
            position++;
        }
        
        return -1;  // Not in waitlist
    }
    
    // 5. Promote first person from waitlist when slot becomes available
    @Override
    public boolean promoteFromWaitlist(int slotId) {
        Queue<Waitlist> queue = slotWaitlists.get(slotId);
        
        if (queue == null || queue.isEmpty()) {
            System.out.println("⚠️ No one in waitlist for this slot.");
            return false;
        }
        
        // Remove first person from queue (FIFO)
        Waitlist promoted = queue.poll();
        
        if (promoted != null) {
            waitlistEntries.remove(promoted.getWaitlistId());
            
            // Automatically book the slot for this user
            if (bookingService != null) {
                bookingService.bookSlot(promoted.getUserId(), slotId, new Date());
            }
            
            // Update priority positions for remaining entries
            updatePriorityPositions(slotId);
            
            System.out.println("✅ User promoted from waitlist!");
            System.out.println("   User ID: " + promoted.getUserId());
            System.out.println("   Slot ID: " + slotId);
            
            return true;
        }
        
        return false;
    }
    
    // 6. Check if user is in waitlist for a slot
    @Override
    public boolean isUserInWaitlist(int userId, int slotId) {
        Queue<Waitlist> queue = slotWaitlists.get(slotId);
        
        if (queue == null) {
            return false;
        }
        
        for (Waitlist entry : queue) {
            if (entry.getUserId() == userId) {
                return true;
            }
        }
        
        return false;
    }
    
    // 7. Get waitlist entry by ID
    public Waitlist getWaitlistById(int waitlistId) {
        return waitlistEntries.get(waitlistId);
    }
    
    // 8. Get all waitlist entries for a user (across all slots)
    public List<Waitlist> getWaitlistByUser(int userId) {
        List<Waitlist> userWaitlist = new ArrayList<>();
        
        for (Waitlist entry : waitlistEntries.values()) {
            if (entry.getUserId() == userId) {
                userWaitlist.add(entry);
            }
        }
        
        return userWaitlist;
    }
    
    // 9. Get total waitlist count for a slot
    public int getWaitlistCount(int slotId) {
        Queue<Waitlist> queue = slotWaitlists.get(slotId);
        return queue != null ? queue.size() : 0;
    }
    
    // 10. Clear waitlist for a slot (Admin functionality)
    public boolean clearWaitlist(int slotId) {
        Queue<Waitlist> queue = slotWaitlists.remove(slotId);
        
        if (queue != null) {
            // Remove all entries from main map
            for (Waitlist entry : queue) {
                waitlistEntries.remove(entry.getWaitlistId());
            }
            
            System.out.println("✅ Waitlist cleared for slot ID: " + slotId);
            return true;
        }
        
        System.out.println("⚠️ No waitlist found for slot ID: " + slotId);
        return false;
    }
    
    // 11. Get all waitlist entries (Admin view)
    public List<Waitlist> getAllWaitlistEntries() {
        return new ArrayList<>(waitlistEntries.values());
    }
    
    // 12. Update priority positions after removal (helper method)
    private void updatePriorityPositions(int slotId) {
        Queue<Waitlist> queue = slotWaitlists.get(slotId);
        
        if (queue == null) {
            return;
        }
        
        int position = 1;
        for (Waitlist entry : queue) {
            entry.setPriorityPosition(position++);
        }
    }
    
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
    
    // 15. Display all waitlists
    public void displayAllWaitlists() {
        if (waitlistEntries.isEmpty()) {
            System.out.println("No waitlist entries in the system!");
        } else {
            System.out.println("\n=== ALL WAITLIST ENTRIES ===");
            System.out.println("Total entries: " + waitlistEntries.size());
            
            for (Map.Entry<Integer, Queue<Waitlist>> entry : slotWaitlists.entrySet()) {
                int slotId = entry.getKey();
                Queue<Waitlist> queue = entry.getValue();
                
                if (!queue.isEmpty()) {
                    System.out.println("\n--- Slot ID: " + slotId + " ---");
                    for (Waitlist w : queue) {
                        System.out.println("  Position " + w.getPriorityPosition() + 
                                         ": User ID " + w.getUserId());
                    }
                }
            }
        }
    }
    
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