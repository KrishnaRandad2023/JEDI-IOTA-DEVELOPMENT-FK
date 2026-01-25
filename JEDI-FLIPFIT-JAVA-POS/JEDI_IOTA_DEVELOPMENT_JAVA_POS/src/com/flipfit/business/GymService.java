package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.GymCenter;

public class GymService implements GymServiceInterface {
    // Collections
    private Map<Integer, GymCenter> gymCenters;              // centerId -> GymCenter
    private Map<Integer, List<GymCenter>> ownerCenters;      // ownerId -> List of Centers
    private Map<String, List<GymCenter>> cityCenters;        // city -> List of Centers
    private List<GymCenter> pendingApprovals;                // Centers waiting for approval
    private Set<Integer> approvedCenterIds;                  // Set of approved center IDs
    private int centerIdCounter;
    
    // Constructor with hard-coded data
    public GymService() {
        this.gymCenters = new HashMap<>();
        this.ownerCenters = new HashMap<>();
        this.cityCenters = new HashMap<>();
        this.pendingApprovals = new ArrayList<>();
        this.approvedCenterIds = new HashSet<>();
        this.centerIdCounter = 1;
        
        // Initialize with hard-coded gym centers
        initializeHardcodedCenters();
    }
    
    // Initialize hard-coded data
    private void initializeHardcodedCenters() {
        // Owner 1 (Rajesh - userId=2) Centers - APPROVED
        addGymCenterInternal(2, "Bellandur Fitness", "123 Bellandur Main Road", "Bangalore", 50, true);
        addGymCenterInternal(2, "HSR Fitness Hub", "456 HSR Layout Sector 2", "Bangalore", 40, true);
        
        // Owner 2 (Priya - userId=3) Centers - APPROVED
        addGymCenterInternal(3, "Indiranagar Gym", "789 Indiranagar 100 Feet Road", "Bangalore", 30, true);
        addGymCenterInternal(3, "Koramangala Fitness", "321 Koramangala 5th Block", "Bangalore", 35, true);
        
        // PENDING APPROVALS (not yet approved)
        addGymCenterInternal(2, "Whitefield Sports Club", "555 Whitefield Main Road", "Bangalore", 60, false);
        addGymCenterInternal(3, "Electronic City Gym", "888 Electronic City Phase 1", "Bangalore", 45, false);
        
        System.out.println("✅ GymService initialized with " + gymCenters.size() + " centers");
        System.out.println("   - Approved: " + approvedCenterIds.size());
        System.out.println("   - Pending: " + pendingApprovals.size());
    }
    
    // Helper method to add centers during initialization
    private void addGymCenterInternal(int ownerId, String name, String address, String city, int capacity, boolean isApproved) {
        GymCenter center = new GymCenter();
        center.setCenterId(centerIdCounter++);
        center.setOwnerId(ownerId);
        center.setCenterName(name);
        center.setAddress(address);
        center.setCity(city);
        center.setCapacity(capacity);
        center.setApproved(isApproved);
        
        // Add to main map
        gymCenters.put(center.getCenterId(), center);
        
        // Add to owner's list
        ownerCenters.computeIfAbsent(ownerId, k -> new ArrayList<>()).add(center);
        
        // Add to city list
        cityCenters.computeIfAbsent(city, k -> new ArrayList<>()).add(center);
        
        // Add to appropriate approval list
        if (isApproved) {
            approvedCenterIds.add(center.getCenterId());
        } else {
            pendingApprovals.add(center);
        }
    }
    
    // 1. View all centers in a specific city
    @Override
    public List<GymCenter> viewAllCenters(String city) {
        List<GymCenter> centersInCity = cityCenters.getOrDefault(city, new ArrayList<>());
        
        // Filter only approved centers for public viewing
        List<GymCenter> approvedCenters = new ArrayList<>();
        for (GymCenter center : centersInCity) {
            if (center.isApproved()) {
                approvedCenters.add(center);
            }
        }
        
        return approvedCenters;
    }
    
    // 2. Check if center is available (has approved status)
    @Override
    public boolean isCenterAvailable(int centerId, Date date) {
        GymCenter center = gymCenters.get(centerId);
        // For now, just check if center exists and is approved
        // In real scenario, would check against slot bookings for the date
        return center != null && center.isApproved();
    }
    
    // 3. Add new gym center (Gym Owner functionality)
    public boolean addGymCenter(GymCenter center) {
        // Assign new center ID
        center.setCenterId(centerIdCounter++);
        
        // New centers require admin approval
        center.setApproved(false);
        
        // Add to collections
        gymCenters.put(center.getCenterId(), center);
        ownerCenters.computeIfAbsent(center.getOwnerId(), k -> new ArrayList<>()).add(center);
        cityCenters.computeIfAbsent(center.getCity(), k -> new ArrayList<>()).add(center);
        pendingApprovals.add(center);
        
        System.out.println("✅ Gym center added! Pending admin approval.");
        System.out.println("   Center ID: " + center.getCenterId());
        return true;
    }
    
    // 4. Update gym center details
    public boolean updateGymCenter(GymCenter center) {
        if (!gymCenters.containsKey(center.getCenterId())) {
            System.out.println("❌ Center not found!");
            return false;
        }
        
        GymCenter oldCenter = gymCenters.get(center.getCenterId());
        
        // Update in main map
        gymCenters.put(center.getCenterId(), center);
        
        // Update in owner's list
        List<GymCenter> ownerCenterList = ownerCenters.get(center.getOwnerId());
        if (ownerCenterList != null) {
            for (int i = 0; i < ownerCenterList.size(); i++) {
                if (ownerCenterList.get(i).getCenterId() == center.getCenterId()) {
                    ownerCenterList.set(i, center);
                    break;
                }
            }
        }
        
        // Update in city list (if city changed)
        if (!oldCenter.getCity().equals(center.getCity())) {
            // Remove from old city
            List<GymCenter> oldCityList = cityCenters.get(oldCenter.getCity());
            if (oldCityList != null) {
                oldCityList.removeIf(c -> c.getCenterId() == center.getCenterId());
            }
            
            // Add to new city
            cityCenters.computeIfAbsent(center.getCity(), k -> new ArrayList<>()).add(center);
        }
        
        System.out.println("✅ Center updated successfully!");
        return true;
    }
    
    // 5. Get center by ID
    public GymCenter getCenterById(int centerId) {
        return gymCenters.get(centerId);
    }
    
    // 6. Get all centers owned by a specific owner
    public List<GymCenter> getCentersByOwner(int ownerId) {
        return ownerCenters.getOrDefault(ownerId, new ArrayList<>());
    }
    
    // 7. Get centers pending admin approval
    public List<GymCenter> getPendingApprovals() {
        return new ArrayList<>(pendingApprovals);
    }
    
    // 8. Admin approves a gym center
    public boolean approveCenterRegistration(int centerId) {
        GymCenter center = gymCenters.get(centerId);
        
        if (center == null) {
            System.out.println("❌ Center not found!");
            return false;
        }
        
        if (center.isApproved()) {
            System.out.println("⚠️ Center already approved!");
            return false;
        }
        
        // Approve the center
        center.setApproved(true);
        approvedCenterIds.add(centerId);
        
        // Remove from pending list
        pendingApprovals.removeIf(c -> c.getCenterId() == centerId);
        
        System.out.println("✅ Center approved successfully!");
        System.out.println("   Center: " + center.getCenterName());
        return true;
    }
    
    // 9. Admin rejects a gym center
    public boolean rejectCenterRegistration(int centerId) {
        GymCenter center = gymCenters.get(centerId);
        
        if (center == null) {
            System.out.println("❌ Center not found!");
            return false;
        }
        
        // Remove from all collections
        gymCenters.remove(centerId);
        pendingApprovals.removeIf(c -> c.getCenterId() == centerId);
        
        // Remove from owner's list
        List<GymCenter> ownerCenterList = ownerCenters.get(center.getOwnerId());
        if (ownerCenterList != null) {
            ownerCenterList.removeIf(c -> c.getCenterId() == centerId);
        }
        
        // Remove from city list
        List<GymCenter> cityCenterList = cityCenters.get(center.getCity());
        if (cityCenterList != null) {
            cityCenterList.removeIf(c -> c.getCenterId() == centerId);
        }
        
        System.out.println("✅ Center registration rejected and removed!");
        return true;
    }
    
    // 10. Get all gym centers (Admin view)
    public List<GymCenter> getAllCenters() {
        return new ArrayList<>(gymCenters.values());
    }
    
    // 11. Get only approved centers
    public List<GymCenter> getApprovedCenters() {
        List<GymCenter> approved = new ArrayList<>();
        for (GymCenter center : gymCenters.values()) {
            if (center.isApproved()) {
                approved.add(center);
            }
        }
        return approved;
    }
    
    // 12. Delete gym center (Owner or Admin)
    public boolean deleteGymCenter(int centerId) {
        GymCenter center = gymCenters.remove(centerId);
        
        if (center != null) {
            // Remove from owner's list
            List<GymCenter> ownerCenterList = ownerCenters.get(center.getOwnerId());
            if (ownerCenterList != null) {
                ownerCenterList.removeIf(c -> c.getCenterId() == centerId);
            }
            
            // Remove from city list
            List<GymCenter> cityCenterList = cityCenters.get(center.getCity());
            if (cityCenterList != null) {
                cityCenterList.removeIf(c -> c.getCenterId() == centerId);
            }
            
            // Remove from approval lists
            approvedCenterIds.remove(centerId);
            pendingApprovals.removeIf(c -> c.getCenterId() == centerId);
            
            System.out.println("✅ Center deleted successfully!");
            return true;
        }
        
        System.out.println("❌ Center not found!");
        return false;
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