package com.flipfit.business;

import java.util.Date;
import java.util.List;

import com.flipfit.bean.*;

/**
 * Interface for Gym Operations
 * 
 * @author team IOTA
 */
public interface GymServiceInterface {
	/**
	 * Retrieves all approved gym centers in a specific city.
	 * 
	 * @param city the name of the city
	 * @return a list of approved gym centers in that city
	 */
	List<GymCenter> viewAllCenters(String city);

	/**
	 * Checks if a gym center is available and approved for booking on a given date.
	 * 
	 * @param centerId the unique ID of the gym center
	 * @param date     the date of interest
	 * @return true if the center exists and is approved, false otherwise
	 */
	boolean isCenterAvailable(int centerId, Date date);
}
