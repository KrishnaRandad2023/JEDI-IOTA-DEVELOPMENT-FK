package com.flipfit.business;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCenter;

/**
 * Interface for Gym Admin Service operations
 */
public interface IGymAdminService {
    
    /**
     * Verify gym owner
     * @param ownerId Owner ID to verify
     * @return boolean indicating success
     */
    boolean verifyGymOwner(int ownerId);
    
    /**
     * Approve gym center
     * @param centerId Center ID to approve
     * @return boolean indicating success
     */
    boolean approveGymCenter(int centerId);
    
    /**
     * View all pending gym owner requests
     */
    void viewPendingOwnerRequests();
    
    /**
     * View all pending gym center requests
     */
    void viewPendingCenterRequests();
    
    /**
     * Reject gym owner request
     * @param ownerId Owner ID to reject
     * @return boolean indicating success
     */
    boolean rejectGymOwner(int ownerId);
}
