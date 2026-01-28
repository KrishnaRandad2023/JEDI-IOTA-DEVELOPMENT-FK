package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Registration;
import java.util.List;

/**
 * Interface for Gym Admin DAO
 * 
 * @author team IOTA
 */
public interface GymAdminDAO {
    List<GymOwner> viewGymOwners();

    List<GymCenter> viewGymCenters();

    boolean approveGymOwner(int ownerId);

    boolean approveGymCenter(int centerId);

    List<GymCenter> viewPendingGymCenters();

    List<Registration> viewPendingRegistrations();
}
