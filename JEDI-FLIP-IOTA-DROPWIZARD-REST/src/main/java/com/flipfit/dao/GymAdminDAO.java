package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Registration;
import java.util.List;

/**
 * Data Access Object interface for system administrative operations.
 * Provides methods for viewing gym owners, centers, and managing registrations.
 * 
 * @author team IOTA
 */
public interface GymAdminDAO {

    /**
     * Retrieves a list of all gym owners in the system.
     * 
     * @return a list of all registered GymOwner objects
     */
    List<GymOwner> viewGymOwners();

    /**
     * Retrieves a list of all gym centers in the system.
     * 
     * @return a list of all GymCenter objects
     */
    List<GymCenter> viewGymCenters();

    /**
     * Approves a pending gym owner registration.
     * 
     * @param ownerId the unique ID of the owner to approve
     * @return true if the approval was successful
     */
    boolean approveGymOwner(int ownerId);

    /**
     * Approves a pending gym center request.
     * 
     * @param centerId the unique ID of the center to approve
     * @return true if the approval was successful
     */
    boolean approveGymCenter(int centerId);

    /**
     * Retrieves a list of gym centers that are awaiting approval.
     * 
     * @return a list of pending GymCenter objects
     */
    List<GymCenter> viewPendingGymCenters();

    /**
     * Retrieves a list of user registrations that are awaiting approval.
     * 
     * @return a list of pending Registration objects
     */
    List<Registration> viewPendingRegistrations();
}
