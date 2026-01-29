package com.flipfit.dao;

import com.flipfit.bean.Slot;
import java.util.List;

/**
 * Data Access Object interface for managing gym slot data.
 * Handles CRUD operations for slots within gym centers.
 * 
 * @author team IOTA
 */
public interface SlotDAO {

    /**
     * Adds a new slot to a gym center.
     * 
     * @param slot the Slot object to be added
     * @return true if the slot was successfully added
     */
    boolean addSlot(Slot slot);

    /**
     * Retrieves a slot by its unique persistent ID.
     * 
     * @param slotId the unique slot ID
     * @return the Slot object, or null if not found
     */
    Slot getSlotById(int slotId);

    /**
     * Retrieves all slots associated with a specific gym center.
     * 
     * @param centerId the ID of the gym center
     * @return a list of slots for that center
     */
    List<Slot> getSlotsByCenter(int centerId);

    /**
     * Updates an existing slot's details (e.g., availability).
     * 
     * @param slot the Slot object with updated information
     * @return true if the update was successful
     */
    boolean updateSlot(Slot slot);

    /**
     * Deletes a slot from the system.
     * 
     * @param slotId the ID of the slot to delete
     * @return true if deletion was successful
     */
    boolean deleteSlot(int slotId);
}
