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
	List<GymCenter> viewAllCenters(String city);

	boolean isCenterAvailable(int centerId, Date date);

}
