package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IGymService interface
 */
public class GymService implements IGymService {
    
    private List<GymCenter> gymCenters;
    
    public GymService() {
        this.gymCenters = new ArrayList<>();
    }
    
    @Override
    public List<GymCenter> getAllCenters() {
        // TODO: Implement method to get all gym centers
        return gymCenters;
    }
    
    @Override
    public GymCenter getCenterById(int centerId) {
        // TODO: Implement method to get gym center by ID
        for (GymCenter center : gymCenters) {
            if (center.getCenterId() == centerId) {
                return center;
            }
        }
        return null;
    }
    
    @Override
    public List<GymCenter> getCentersByCity(String city) {
        // TODO: Implement method to get gym centers by city
        List<GymCenter> centersByCity = new ArrayList<>();
        for (GymCenter center : gymCenters) {
            if (center.getCity().equalsIgnoreCase(city)) {
                centersByCity.add(center);
            }
        }
        return centersByCity;
    }
}
