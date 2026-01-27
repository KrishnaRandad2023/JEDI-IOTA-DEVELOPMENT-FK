package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.List;

public interface GymOwnerDAO {
    boolean addGymCenter(GymCenter gymCenter);

    boolean addSlot(Slot slot);

    List<GymCenter> viewMyGymCenters(int ownerId);

    boolean updateGymCenter(GymCenter gymCenter);

    boolean deleteGymCenter(int centerId);

    GymCenter getGymCenterById(int centerId);
}
