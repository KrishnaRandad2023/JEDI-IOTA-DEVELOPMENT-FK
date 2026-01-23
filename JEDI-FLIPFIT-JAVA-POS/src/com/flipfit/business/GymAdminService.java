package com.flipfit.business;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IGymAdminService interface
 */
public class GymAdminService implements IGymAdminService {
    
    private List<GymOwner> pendingOwners;
    private List<GymCenter> pendingCenters;
    
    public GymAdminService() {
        this.pendingOwners = new ArrayList<>();
        this.pendingCenters = new ArrayList<>();
    }
    
    @Override
    public boolean verifyGymOwner(int ownerId) {
        // TODO: Implement method to verify gym owner
        System.out.println("Verifying gym owner with ID: " + ownerId);
        return true;
    }
    
    @Override
    public boolean approveGymCenter(int centerId) {
        // TODO: Implement method to approve gym center
        System.out.println("Approving gym center with ID: " + centerId);
        return true;
    }
    
    @Override
    public void viewPendingOwnerRequests() {
        // TODO: Implement method to view pending owner requests
        System.out.println("Viewing pending owner requests...");
        for (GymOwner owner : pendingOwners) {
            System.out.println("Owner ID: " + owner.getUserId() + ", Name: " + owner.getUserName());
        }
    }
    
    @Override
    public void viewPendingCenterRequests() {
        // TODO: Implement method to view pending center requests
        System.out.println("Viewing pending center requests...");
        for (GymCenter center : pendingCenters) {
            System.out.println("Center ID: " + center.getCenterId() + ", Name: " + center.getCenterName());
        }
    }
    
    @Override
    public boolean rejectGymOwner(int ownerId) {
        // TODO: Implement method to reject gym owner
        System.out.println("Rejecting gym owner with ID: " + ownerId);
        return true;
    }
}
