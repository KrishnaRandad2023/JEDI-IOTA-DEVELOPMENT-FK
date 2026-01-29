package com.flipfit.business;

import java.time.LocalTime;
import java.util.*;
import com.flipfit.bean.Slot;
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.SlotDAOImpl;

/**
 * Service class for managing gym time slots.
 * Handles adding, updating, and deleting slots, as well as managing seat
 * capacity.
 * 
 * @author team IOTA
 */
public class SlotService {
    private SlotDAO slotDAO = new SlotDAOImpl();

    /**
     * Instantiates a new slot service and initializes the DAO.
     */
    public SlotService() {
        System.out.println("✅ SlotService initialized with Database DAO");
    }

    /**
     * Sets the slot DAO implementation.
     *
     * @param slotDAO the slot DAO to use
     */
    public void setSlotDAO(SlotDAO slotDAO) {
        this.slotDAO = slotDAO;
    }

    /**
     * Adds a new time slot to a gym center.
     *
     * @param centerId   the ID of the gym center
     * @param startTime  the start time of the slot
     * @param endTime    the end time of the slot
     * @param totalSeats the total capacity of the slot
     * @return true if the slot was added successfully
     */
    public boolean addSlot(int centerId, LocalTime startTime, LocalTime endTime, int totalSeats) {
        Slot slot = new Slot();
        slot.setCenterId(centerId);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setTotalSeats(totalSeats);
        slot.setAvailableSeats(totalSeats);

        return slotDAO.addSlot(slot);
    }

    /**
     * Retrieves slot details by its unique ID.
     *
     * @param slotId the unique ID of the slot
     * @return the Slot object
     */
    public Slot getSlotById(int slotId) {
        return slotDAO.getSlotById(slotId);
    }

    /**
     * Retrieves all slots for a specific gym center.
     *
     * @param centerId the ID of the gym center
     * @return a list of slots for that center
     */
    public List<Slot> getSlotsByCenter(int centerId) {
        return slotDAO.getSlotsByCenter(centerId);
    }

    /**
     * Retrieves only the slots that have available seats for a specific gym center.
     *
     * @param centerId the ID of the gym center
     * @return a list of available slots
     */
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

    /**
     * Updates an existing slot's details.
     *
     * @param updatedSlot the Slot object with updated information
     * @return true if the update was successful
     */
    public boolean updateSlot(Slot updatedSlot) {
        return slotDAO.updateSlot(updatedSlot);
    }

    /**
     * Deletes a specific slot from the system.
     *
     * @param slotId the unique ID of the slot
     * @return true if deletion was successful
     */
    public boolean deleteSlot(int slotId) {
        return slotDAO.deleteSlot(slotId);
    }

    /**
     * Checks if a slot has at least one available seat.
     *
     * @param slotId the ID of the slot to check
     * @return true if available seats > 0
     */
    public boolean hasAvailableSeats(int slotId) {
        Slot slot = slotDAO.getSlotById(slotId);
        return slot != null && slot.getAvailableSeats() > 0;
    }

    /**
     * Decreases the available seat count for a slot by 1 (e.g., when a booking is
     * confirmed).
     *
     * @param slotId the ID of the slot
     * @return true if seat count was successfully decreased
     */
    public boolean decreaseAvailableSeats(int slotId) {
        Slot slot = slotDAO.getSlotById(slotId);
        if (slot != null && slot.getAvailableSeats() > 0) {
            slot.setAvailableSeats(slot.getAvailableSeats() - 1);
            return slotDAO.updateSlot(slot);
        }
        return false;
    }

    /**
     * Increases the available seat count for a slot by 1 (e.g., when a booking is
     * cancelled).
     *
     * @param slotId the ID of the slot
     * @return true if seat count was successfully increased
     */
    public boolean increaseAvailableSeats(int slotId) {
        Slot slot = slotDAO.getSlotById(slotId);
        if (slot != null && slot.getAvailableSeats() < slot.getTotalSeats()) {
            slot.setAvailableSeats(slot.getAvailableSeats() + 1);
            return slotDAO.updateSlot(slot);
        }
        return false;
    }

    /**
     * Retrieves all slots in the system.
     *
     * @return a list of all slots
     */
    public List<Slot> getAllSlots() {
        // Typically admin views centers then slots.
        return new ArrayList<>();
    }

    /**
     * Helper method to display slot details to the console.
     *
     * @param slot the slot to display
     */
    public void displaySlot(Slot slot) {
        if (slot != null) {
            System.out.println("Slot ID: " + slot.getSlotId());
            System.out.println("Center ID: " + slot.getCenterId());
            System.out.println("Time: " + slot.getStartTime() + " - " + slot.getEndTime());
            System.out.println("Available Seats: " + slot.getAvailableSeats() + "/" + slot.getTotalSeats());
            System.out.println("---");
        }
    }

    /**
     * Displays all slots for a center to the console.
     *
     * @param centerId the ID of the gym center
     */
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