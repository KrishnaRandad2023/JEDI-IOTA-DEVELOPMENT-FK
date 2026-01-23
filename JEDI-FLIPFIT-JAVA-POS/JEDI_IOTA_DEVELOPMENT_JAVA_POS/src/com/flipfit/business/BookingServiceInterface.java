package com.flipfit.business;

import java.util.Date;

public interface BookingServiceInterface {
	boolean checkAvailability(int slotId, Date date);
	void bookSlot(int userId, int slotId, Date date);
	void cancelBooking(int bookingId);
	
}
