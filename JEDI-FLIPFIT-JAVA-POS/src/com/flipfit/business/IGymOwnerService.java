package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.List;

/**
 * Interface for Gym Owner Service operations
 */
public interface IGymOwnerService {
    
    /**
     * Add a new gym center
     * @param gymCenter GymCenter object to add
     * @return boolean indicating success
     */
    boolean addGymCenter(GymCenter gymCenter);
    
    /**
     * Remove gym center by ID
     * @param centerId Center ID to remove
     * @return boolean indicating success
     */
    boolean removeGymCenter(int centerId);
    
    /**
     * Update gym center details
     * @param gymCenter GymCenter object with updated details
     * @return boolean indicating success
     */
    boolean updateGymCenter(GymCenter gymCenter);
    
    /**
     * Add slot to a gym center
     * @param slot Slot object to add
     * @return boolean indicating success
     */
    boolean addSlot(Slot slot);
    
    /**
     * View all centers owned by the owner
     * @param ownerId Owner ID
     * @return List of gym centers
     */
    List<GymCenter> viewMyCenter(int ownerId);
}
