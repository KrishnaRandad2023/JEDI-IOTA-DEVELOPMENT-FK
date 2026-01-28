package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.GymCenter;
import com.flipfit.dao.GymOwnerDAO;
import com.flipfit.dao.GymOwnerDAOImpl;
import com.flipfit.dao.GymAdminDAO;
import com.flipfit.dao.GymAdminDAOImpl;
import com.flipfit.dao.GymCustomerDAO;
import com.flipfit.dao.GymCustomerDAOImpl;

/**
 * The Class GymService.
 * 
 * @author team IOTA
 */
public class GymService implements GymServiceInterface {

    private GymOwnerDAO gymOwnerDAO = new GymOwnerDAOImpl();
    private GymAdminDAO gymAdminDAO = new GymAdminDAOImpl();
    private GymCustomerDAO gymCustomerDAO = new GymCustomerDAOImpl();

    // Constructor
    public GymService() {
        System.out.println("✅ GymService initialized with Database DAOs");
    }

    // 1. View all centers in a specific city
    @Override
    public List<GymCenter> viewAllCenters(String city) {
        // Since GymCustomerDAO.viewGyms() returns all approved gyms,
        // we can filter them by city here, or ideally add a method to DAO.
        // For now, fetching all approved gyms and filtering.
        List<GymCenter> allApprovedGyms = gymCustomerDAO.viewGyms();
        List<GymCenter> cityGyms = new ArrayList<>();

        for (GymCenter gym : allApprovedGyms) {
            if (gym.getCity().equalsIgnoreCase(city)) {
                cityGyms.add(gym);
            }
        }
        return cityGyms;
    }

    // 2. Check if center is available (has approved status)
    @Override
    public boolean isCenterAvailable(int centerId, Date date) {
        GymCenter center = gymOwnerDAO.getGymCenterById(centerId);
        return center != null && center.isApproved();
    }

    // 3. Add new gym center (Gym Owner functionality)
    public boolean addGymCenter(GymCenter center) {
        // New centers require admin approval
        center.setApproved(false);
        boolean success = gymOwnerDAO.addGymCenter(center);
        if (success) {
            System.out.println("✅ Gym center added! Pending admin approval.");
        } else {
            System.out.println("❌ Failed to add gym center.");
        }
        return success;
    }

    // 4. Update gym center details
    public boolean updateGymCenter(GymCenter center) {
        if (gymOwnerDAO.getGymCenterById(center.getCenterId()) == null) {
            System.out.println("❌ Center not found!");
            return false;
        }
        boolean success = gymOwnerDAO.updateGymCenter(center);
        if (success) {
            System.out.println("✅ Center updated successfully!");
        }
        return success;
    }

    // 5. Get center by ID
    public GymCenter getCenterById(int centerId) {
        return gymOwnerDAO.getGymCenterById(centerId);
    }

    // 6. Get all centers owned by a specific owner
    public List<GymCenter> getCentersByOwner(int ownerId) {
        return gymOwnerDAO.viewMyGymCenters(ownerId);
    }

    // 7. Get centers pending admin approval
    public List<GymCenter> getPendingApprovals() {
        return gymAdminDAO.viewPendingGymCenters();
    }

    // 8. Admin approves a gym center
    public boolean approveCenterRegistration(int centerId) {
        GymCenter center = gymOwnerDAO.getGymCenterById(centerId);

        if (center == null) {
            System.out.println("❌ Center not found!");
            return false;
        }

        if (center.isApproved()) {
            System.out.println("⚠️ Center already approved!");
            return false;
        }

        // Approve the center using GymAdminDAO
        boolean success = gymAdminDAO.approveGymCenter(centerId);

        if (success) {
            System.out.println("✅ Center approved successfully!");
        }
        return success;
    }

    // 9. Admin rejects a gym center
    public boolean rejectCenterRegistration(int centerId) {
        GymCenter center = gymOwnerDAO.getGymCenterById(centerId);

        if (center == null) {
            System.out.println("❌ Center not found!");
            return false;
        }

        // Rejecting usually implies deleting or setting status to rejected.
        // Assuming deletion for rejection as per original in-memory logic.
        boolean success = gymOwnerDAO.deleteGymCenter(centerId);

        if (success) {
            System.out.println("✅ Center registration rejected and removed!");
        }
        return success;
    }

    // 10. Get all gym centers (Admin view)
    public List<GymCenter> getAllCenters() {
        return gymAdminDAO.viewGymCenters();
    }

    // 11. Get only approved centers
    public List<GymCenter> getApprovedCenters() {
        return gymCustomerDAO.viewGyms();
    }

    // 12. Delete gym center (Owner or Admin)
    public boolean deleteGymCenter(int centerId) {
        if (gymOwnerDAO.getGymCenterById(centerId) == null) {
            System.out.println("❌ Center not found!");
            return false;
        }
        boolean success = gymOwnerDAO.deleteGymCenter(centerId);
        if (success) {
            System.out.println("✅ Center deleted successfully!");
        } else {
            System.out.println("❌ Failed to delete center.");
        }
        return success;
    }

    // 13. Display center details (helper method)
    public void displayCenter(GymCenter center) {
        if (center != null) {
            System.out.println("Center ID: " + center.getCenterId());
            System.out.println("Name: " + center.getCenterName());
            System.out.println("Address: " + center.getAddress());
            System.out.println("City: " + center.getCity());
            System.out.println("Capacity: " + center.getCapacity());
            System.out.println("Owner ID: " + center.getOwnerId());
            System.out.println("Status: " + (center.isApproved() ? "✅ Approved" : "⏳ Pending"));
            System.out.println("---");
        }
    }

    // 14. Display all centers in a city
    public void displayCentersByCity(String city) {
        List<GymCenter> centers = viewAllCenters(city);

        if (centers.isEmpty()) {
            System.out.println("No approved centers found in " + city);
        } else {
            System.out.println("\n=== Gym Centers in " + city + " ===");
            for (GymCenter center : centers) {
                displayCenter(center);
            }
        }
    }

    // 15. Display pending approvals
    public void displayPendingApprovals() {
        List<GymCenter> pendingApprovals = getPendingApprovals();
        if (pendingApprovals.isEmpty()) {
            System.out.println("No pending approvals!");
        } else {
            System.out.println("\n=== Pending Approvals ===");
            for (GymCenter center : pendingApprovals) {
                displayCenter(center);
            }
        }
    }
}