package com.wallmart.tiket.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Booking Table where booking informations stored.
 * @author Anish Jose
 *
 */
@Entity
@Table(name = "BOOKING")
public class Bookings implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id	
	@Column(name = "email", updatable = true, nullable = false)
	private String email;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public String getSeats() {
		return seats;
	}
	public void setSeats(String seats) {
		this.seats = seats;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	private long eventId;
	private String status;
	private int numberOfSeats;
	private String seats;
	private long time;
	
	
}
