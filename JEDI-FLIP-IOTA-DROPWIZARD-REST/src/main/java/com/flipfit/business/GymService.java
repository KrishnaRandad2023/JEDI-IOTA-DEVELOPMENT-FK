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
 * Service class for managing gym centers.
 * Handles tasks such as viewing centers, adding new centers (pending approval),
 * updating center details, and administrative approvals/rejections.
 * 
 * @author team IOTA
 */
public class GymService implements GymServiceInterface {

    private GymOwnerDAO gymOwnerDAO = new GymOwnerDAOImpl();
    private GymAdminDAO gymAdminDAO = new GymAdminDAOImpl();
    private GymCustomerDAO gymCustomerDAO = new GymCustomerDAOImpl();

    /**
     * Instantiates a new gym service and initializes the DAOs.
     */
    public GymService() {
        System.out.println("✅ GymService initialized with Database DAOs");
    }

    /**
     * Retrieves all approved gym centers in a specific city.
     *
     * @param city the name of the city
     * @return a list of approved gym centers in that city
     */
    @Override
    public List<GymCenter> viewAllCenters(String city) {
        List<GymCenter> allApprovedGyms = gymCustomerDAO.viewGyms();
        List<GymCenter> cityGyms = new ArrayList<>();

        for (GymCenter gym : allApprovedGyms) {
            if (gym.getCity().trim().equalsIgnoreCase(city.trim())) {
                cityGyms.add(gym);
            }
        }
        return cityGyms;
    }

    /**
     * Checks if a gym center is available and approved for booking.
     *
     * @param centerId the unique ID of the gym center
     * @param date     the date of interest
     * @return true if the center exists and is approved, false otherwise
     */
    @Override
    public boolean isCenterAvailable(int centerId, Date date) {
        GymCenter center = gymOwnerDAO.getGymCenterById(centerId);
        return center != null && center.isApproved();
    }

    /**
     * Adds a new gym center to the system. The center will be in a pending state
     * until approved by an admin.
     *
     * @param center the GymCenter object containing details
     * @return true if the addition was successful
     */
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

    /**
     * Updates the details of an existing gym center.
     *
     * @param center the GymCenter object with updated details
     * @return true if the update was successful, false if center not found
     */
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

    /**
     * Retrieves center details by its unique ID.
     *
     * @param centerId the unique ID of the center
     * @return the GymCenter object
     */
    public GymCenter getCenterById(int centerId) {
        return gymOwnerDAO.getGymCenterById(centerId);
    }

    /**
     * Retrieves all gym centers owned by a specific owner ID.
     *
     * @param ownerId the user ID of the gym owner
     * @return a list of gym centers
     */
    public List<GymCenter> getCentersByOwner(int ownerId) {
        return gymOwnerDAO.viewMyGymCenters(ownerId);
    }

    /**
     * Retrieves all gym centers currently awaiting administrative approval.
     *
     * @return a list of pending gym centers
     */
    public List<GymCenter> getPendingApprovals() {
        return gymAdminDAO.viewPendingGymCenters();
    }

    /**
     * Approves a gym center's registration request.
     *
     * @param centerId the unique ID of the gym center to approve
     * @return true if approval was successful
     */
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

    /**
     * Rejects a gym center's registration request, effectively deleting it.
     *
     * @param centerId the unique ID of the gym center to reject
     * @return true if rejection was successful
     */
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

    /**
     * Retrieves all gym centers in the system (for admin view).
     *
     * @return a list of all gym centers
     */
    public List<GymCenter> getAllCenters() {
        return gymAdminDAO.viewGymCenters();
    }

    /**
     * Retrieves all approved gym centers in the system.
     *
     * @return a list of approved gym centers
     */
    public List<GymCenter> getApprovedCenters() {
        return gymCustomerDAO.viewGyms();
    }

    /**
     * Deletes a gym center from the system.
     *
     * @param centerId the ID of the center to delete
     * @return true if deletion was successful
     */
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

    /**
     * Helper method to display center details to the console.
     *
     * @param center the center to display
     */
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

    /**
     * Displays all approved gym centers in a specific city to the console.
     * 
     * @param city the name of the city to filter by
     */
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

    /**
     * Displays all gym centers currently awaiting administrative approval to the
     * console.
     */
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