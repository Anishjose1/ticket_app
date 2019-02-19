package com.wallmart.tiket.domain;

import java.io.Serializable;

import javax.persistence.Id;
/***
 * Seat uniquely identify record from Booking
 * @author Anish Jose
 *
 */
public class Seat implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public long getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(long seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	private long seatHoldId;
	private String email;
	private long eventId;
	
	
	
}
