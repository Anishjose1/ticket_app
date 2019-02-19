package com.wallmart.tiket.repo;

import java.util.List;
import com.wallmart.tiket.domain.Facility;
public interface TicketServiceDao {

	public int getReservedSeats(long eventId);
	public List getReservedSeatsForAnEvent(long eventId);
	public List<Integer> getHoldedSeatsForAnEvent(long eventId);
	public boolean reserveSeats(long seatHoldId,String email,long eventId);
	public void findOutAndReleaseSeatsOnHold(List<Facility> events);
}
