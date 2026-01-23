package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IGymOwnerService interface
 */
public class GymOwnerService implements IGymOwnerService {
    
    private List<GymCenter> gymCenters;
    
    public GymOwnerService() {
        this.gymCenters = new ArrayList<>();
    }
    
    @Override
    public boolean addGymCenter(GymCenter gymCenter) {
        // TODO: Implement method to add gym center
        System.out.println("Adding gym center: " + gymCenter.getCenterName());
        gymCenters.add(gymCenter);
        return true;
    }
    
    @Override
    public boolean removeGymCenter(int centerId) {
        // TODO: Implement method to remove gym center
        System.out.println("Removing gym center with ID: " + centerId);
        return gymCenters.removeIf(center -> center.getCenterId() == centerId);
    }
    
    @Override
    public boolean updateGymCenter(GymCenter gymCenter) {
        // TODO: Implement method to update gym center
        System.out.println("Updating gym center: " + gymCenter.getCenterName());
        for (int i = 0; i < gymCenters.size(); i++) {
            if (gymCenters.get(i).getCenterId() == gymCenter.getCenterId()) {
                gymCenters.set(i, gymCenter);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean addSlot(Slot slot) {
        // TODO: Implement method to add slot to a gym center
        System.out.println("Adding slot with ID: " + slot.getSlotId());
        return true;
    }
    
    @Override
    public List<GymCenter> viewMyCenter(int ownerId) {
        // TODO: Implement method to view centers owned by owner
        List<GymCenter> ownedCenters = new ArrayList<>();
        for (GymCenter center : gymCenters) {
            if (center.getOwnerId() == ownerId) {
                ownedCenters.add(center);
            }
        }
        return ownedCenters;
    }
}
