package com.flipfit.business;

import java.time.LocalTime;
import java.util.*;
import com.flipfit.bean.Slot;

public class SlotService {
    // Collections - Using Map for fast lookups and List for grouping
    private Map<Integer, Slot> slots;                          // slotId -> Slot
    private Map<Integer, List<Slot>> centerSlots;              // centerId -> List of Slots
    private int slotIdCounter;
    
    // Constructor with hard-coded data
    public SlotService() {
        this.slots = new HashMap<>();
        this.centerSlots = new HashMap<>();
        this.slotIdCounter = 1;
        
        // Initialize with hard-coded slots
        initializeHardcodedSlots();
    }
    
    // Initialize hard-coded data
    private void initializeHardcodedSlots() {
        // Slots for Center 1 (Bellandur Fitness)
        addSlot(1, LocalTime.of(6, 0), LocalTime.of(7, 0), 20);   // 6-7 AM
        addSlot(1, LocalTime.of(7, 0), LocalTime.of(8, 0), 20);   // 7-8 AM
        addSlot(1, LocalTime.of(18, 0), LocalTime.of(19, 0), 25); // 6-7 PM
        addSlot(1, LocalTime.of(19, 0), LocalTime.of(20, 0), 25); // 7-8 PM
        
        // Slots for Center 2 (HSR Fitness Hub)
        addSlot(2, LocalTime.of(6, 0), LocalTime.of(7, 0), 15);   // 6-7 AM
        addSlot(2, LocalTime.of(17, 0), LocalTime.of(18, 0), 20); // 5-6 PM
        addSlot(2, LocalTime.of(18, 0), LocalTime.of(19, 0), 20); // 6-7 PM
        
        // Slots for Center 3 (Indiranagar Gym)
        addSlot(3, LocalTime.of(5, 0), LocalTime.of(6, 0), 10);   // 5-6 AM
        addSlot(3, LocalTime.of(6, 0), LocalTime.of(7, 0), 15);   // 6-7 AM
        addSlot(3, LocalTime.of(18, 0), LocalTime.of(19, 0), 15); // 6-7 PM
        
        System.out.println("✅ SlotService initialized with " + slots.size() + " slots");
    }
    
    // 1. Add new slot (used by Gym Owner)
    public boolean addSlot(int centerId, LocalTime startTime, LocalTime endTime, int totalSeats) {
        Slot slot = new Slot();
        slot.setSlotId(slotIdCounter++);
        slot.setCenterId(centerId);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setTotalSeats(totalSeats);
        slot.setAvailableSeats(totalSeats);  // Initially all seats available
        
        // Add to main map
        slots.put(slot.getSlotId(), slot);
        
        // Add to center-specific list
        centerSlots.computeIfAbsent(centerId, k -> new ArrayList<>()).add(slot);
        
        return true;
    }
    
    // 2. Get slot by ID
    public Slot getSlotById(int slotId) {
        return slots.get(slotId);
    }
    
    // 3. Get all slots for a specific gym center
    public List<Slot> getSlotsByCenter(int centerId) {
        return centerSlots.getOrDefault(centerId, new ArrayList<>());
    }
    
    // 4. Get all available slots for a center (with seats > 0)
    public List<Slot> getAvailableSlotsByCenter(int centerId) {
        List<Slot> centerSlotList = centerSlots.getOrDefault(centerId, new ArrayList<>());
        List<Slot> availableSlots = new ArrayList<>();
        
        for (Slot slot : centerSlotList) {
            if (slot.getAvailableSeats() > 0) {
                availableSlots.add(slot);
            }
        }
        
        return availableSlots;
    }
    
    // 5. Update slot (used by Gym Owner)
    public boolean updateSlot(Slot updatedSlot) {
        if (slots.containsKey(updatedSlot.getSlotId())) {
            slots.put(updatedSlot.getSlotId(), updatedSlot);
            
            // Update in center list as well
            List<Slot> centerSlotList = centerSlots.get(updatedSlot.getCenterId());
            if (centerSlotList != null) {
                for (int i = 0; i < centerSlotList.size(); i++) {
                    if (centerSlotList.get(i).getSlotId() == updatedSlot.getSlotId()) {
                        centerSlotList.set(i, updatedSlot);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    // 6. Delete slot (used by Gym Owner)
    public boolean deleteSlot(int slotId) {
        Slot slot = slots.remove(slotId);
        if (slot != null) {
            // Remove from center list
            List<Slot> centerSlotList = centerSlots.get(slot.getCenterId());
            if (centerSlotList != null) {
                centerSlotList.removeIf(s -> s.getSlotId() == slotId);
            }
            return true;
        }
        return false;
    }
    
    // 7. Check if slot has available seats
    public boolean hasAvailableSeats(int slotId) {
        Slot slot = slots.get(slotId);
        return slot != null && slot.getAvailableSeats() > 0;
    }
    
    // 8. Decrease available seats (when booking)
    public boolean decreaseAvailableSeats(int slotId) {
        Slot slot = slots.get(slotId);
        if (slot != null && slot.getAvailableSeats() > 0) {
            slot.setAvailableSeats(slot.getAvailableSeats() - 1);
            return true;
        }
        return false;
    }
    
    // 9. Increase available seats (when canceling)
    public boolean increaseAvailableSeats(int slotId) {
        Slot slot = slots.get(slotId);
        if (slot != null && slot.getAvailableSeats() < slot.getTotalSeats()) {
            slot.setAvailableSeats(slot.getAvailableSeats() + 1);
            return true;
        }
        return false;
    }
    
    // 10. Get all slots (for admin view)
    public List<Slot> getAllSlots() {
        return new ArrayList<>(slots.values());
    }
    
    // 11. Display slot details (helper method)
    public void displaySlot(Slot slot) {
        if (slot != null) {
            System.out.println("Slot ID: " + slot.getSlotId());
            System.out.println("Center ID: " + slot.getCenterId());
            System.out.println("Time: " + slot.getStartTime() + " - " + slot.getEndTime());
            System.out.println("Available Seats: " + slot.getAvailableSeats() + "/" + slot.getTotalSeats());
            System.out.println("---");
        }
    }
    
    // 12. Display all slots for a center
    public void displaySlotsByCenter(int centerId) {
        List<Slot> centerSlotList = getSlotsByCenter(centerId);
        if (centerSlotList.isEmpty()) {
            System.out.println("No slots found for center ID: " + centerId);
        } else {
            System.out.println("\n=== Slots for Center ID: " + centerId + " ===");
            for (Slot slot : centerSlotList) {
                displaySlot(slot);
            }
        }
    }
}