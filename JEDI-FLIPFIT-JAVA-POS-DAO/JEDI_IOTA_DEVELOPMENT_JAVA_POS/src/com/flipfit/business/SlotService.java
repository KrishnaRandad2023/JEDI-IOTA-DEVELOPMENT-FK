package com.flipfit.business;

import java.time.LocalTime;
import java.util.*;
import com.flipfit.bean.Slot;

/**
 * The Class SlotService.
 * 
 * @author team IOTA
 */
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.SlotDAOImpl;

/**
 * The Class SlotService.
 * 
 * @author team IOTA
 */
public class SlotService {
    private SlotDAO slotDAO = new SlotDAOImpl();

    public SlotService() {
        System.out.println("✅ SlotService initialized with Database DAO");
    }

    public void setSlotDAO(SlotDAO slotDAO) {
        this.slotDAO = slotDAO;
    }

    // 1. Add new slot (used by Gym Owner)
    public boolean addSlot(int centerId, LocalTime startTime, LocalTime endTime, int totalSeats) {
        Slot slot = new Slot();
        slot.setCenterId(centerId);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setTotalSeats(totalSeats);
        slot.setAvailableSeats(totalSeats);

        return slotDAO.addSlot(slot);
    }

    // 2. Get slot by ID
    public Slot getSlotById(int slotId) {
        return slotDAO.getSlotById(slotId);
    }

    // 3. Get all slots for a specific gym center
    public List<Slot> getSlotsByCenter(int centerId) {
        return slotDAO.getSlotsByCenter(centerId);
    }

    // 4. Get all available slots for a center (with seats > 0)
    public List<Slot> getAvailableSlotsByCenter(int centerId) {
        List<Slot> centerSlotList = slotDAO.getSlotsByCenter(centerId);
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
        return slotDAO.updateSlot(updatedSlot);
    }

    // 6. Delete slot (used by Gym Owner)
    public boolean deleteSlot(int slotId) {
        return slotDAO.deleteSlot(slotId);
    }

    // 7. Check if slot has available seats
    public boolean hasAvailableSeats(int slotId) {
        Slot slot = slotDAO.getSlotById(slotId);
        return slot != null && slot.getAvailableSeats() > 0;
    }

    // 8. Decrease available seats (when booking)
    public boolean decreaseAvailableSeats(int slotId) {
        Slot slot = slotDAO.getSlotById(slotId);
        if (slot != null && slot.getAvailableSeats() > 0) {
            slot.setAvailableSeats(slot.getAvailableSeats() - 1);
            return slotDAO.updateSlot(slot);
        }
        return false;
    }

    // 9. Increase available seats (when canceling)
    public boolean increaseAvailableSeats(int slotId) {
        Slot slot = slotDAO.getSlotById(slotId);
        if (slot != null && slot.getAvailableSeats() < slot.getTotalSeats()) {
            slot.setAvailableSeats(slot.getAvailableSeats() + 1);
            return slotDAO.updateSlot(slot);
        }
        return false;
    }

    // 10. Get all slots (for admin view)
    public List<Slot> getAllSlots() {
        // This might need a new DAO method if actually used,
        // but typically admin views centers then slots.
        return new ArrayList<>();
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