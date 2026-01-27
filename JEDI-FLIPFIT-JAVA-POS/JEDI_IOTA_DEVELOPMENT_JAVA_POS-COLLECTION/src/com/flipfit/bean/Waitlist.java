package com.flipfit.bean;

import java.util.Date;

public class Waitlist {
	private int waitlistId;
	private int userId;
    private int slotId;
    private Date requestDate;
    private int priorityPosition; // Used to notify/promote candidates 

    public int getWaitlistId() {
		return waitlistId;
	}
	public void setWaitlistId(int waitlistId) {
		this.waitlistId = waitlistId;
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
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public int getPriorityPosition() {
		return priorityPosition;
	}
	public void setPriorityPosition(int priorityPosition) {
		this.priorityPosition = priorityPosition;
	}
	}
