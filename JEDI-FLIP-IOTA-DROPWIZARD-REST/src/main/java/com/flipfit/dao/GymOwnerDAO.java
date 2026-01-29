package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.List;

/**
 * Data Access Object interface for gym owner operations.
 * Handles gym center management, including adding centers and slots,
 * and performing CRUD operations on owner-owned gym assets.
 * 
 * @author team IOTA
 */
public interface GymOwnerDAO {

    /**
     * Adds a new gym center to the system on behalf of the owner.
     * 
     * @param gymCenter the GymCenter object to be added
     * @return true if the center was successfully added
     */
    boolean addGymCenter(GymCenter gymCenter);

    /**
     * Adds a new time slot to a specific gym center.
     * 
     * @param slot the Slot object to be added
     * @return true if the slot was successfully added
     */
    boolean addSlot(Slot slot);

    /**
     * Retrieves all gym centers owned by a specific owner.
     * 
     * @param ownerId the unique ID of the gym owner
     * @return a list of GymCenter objects belonging to the owner
     */
    List<GymCenter> viewMyGymCenters(int ownerId);

    /**
     * Updates the details of an existing gym center.
     * 
     * @param gymCenter the GymCenter object with updated information
     * @return true if the update was successful
     */
    boolean updateGymCenter(GymCenter gymCenter);

    /**
     * Deletes a gym center from the system.
     * 
     * @param centerId the ID of the gym center to delete
     * @return true if deletion was successful
     */
    boolean deleteGymCenter(int centerId);

    /**
     * Retrieves a gym center by its unique persistent ID.
     * 
     * @param centerId the unique center ID
     * @return the GymCenter object, or null if not found
     */
    GymCenter getGymCenterById(int centerId);
}
