package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import java.util.List;

/**
 * Interface for Gym Service operations
 */
public interface IGymService {
    
    /**
     * Get all gym centers
     * @return List of all gym centers
     */
    List<GymCenter> getAllCenters();
    
    /**
     * Get gym center by ID
     * @param centerId Center ID
     * @return GymCenter object
     */
    GymCenter getCenterById(int centerId);
    
    /**
     * Get gym centers by city
     * @param city City name
     * @return List of gym centers in the specified city
     */
    List<GymCenter> getCentersByCity(String city);
}
