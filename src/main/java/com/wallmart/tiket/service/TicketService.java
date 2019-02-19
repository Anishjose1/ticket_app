package com.wallmart.tiket.service;

import java.util.List;

import com.wallmart.tiket.domain.Message;
import com.wallmart.tiket.domain.SeatHold;

public interface TicketService {
	/**
	* The number of seats in the venue that are neither held nor reserved
	*
	* @return the number of tickets available in the venue
	*/
	int numSeatsAvailable(long eventId);
	/**
	* Find and hold the best available seats for a customer
	*
	* @param numSeats the number of seats to find and hold
	* @param customerEmail unique identifier for the customer
	* @return a SeatHold object identifying the specific seats and related
	information
	*/
	SeatHold findAndHoldSeats(int numSeats, String customerEmail,long eventId);
	/**
	* Commit seats held for a specific customer
	*
	* @param seatHoldId the seat hold identifier
	* @param customerEmail the email address of the customer to which the
	seat hold is assigned
	* @return a reservation confirmation code
	*/
	Message reserveSeats(long seatHoldId, String customerEmail,long eventId);
	/***
	 * Display seats graphical way
	 * @param eventId
	 * @return
	 */
	public List<String> displaySeats(long eventId);
}
