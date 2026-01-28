package com.flipfit.bean;

import java.util.Date;


public class Booking {
	/**
	 *
	 *
	 */

	private int bookingId;
	private int userId;
    private int slotId;
    private Date bookingDate;
    private BookingStatus status; // CONFIRMED, CANCELLED, WAITLISTED 
    
    
    public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSlotId() {
		return slotId;
	}
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public BookingStatus getStatus() {
		return status;
	}
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	
}
