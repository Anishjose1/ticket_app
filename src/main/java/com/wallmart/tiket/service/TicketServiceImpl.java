package com.wallmart.tiket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wallmart.tiket.domain.Bookings;
import com.wallmart.tiket.domain.Message;
import com.wallmart.tiket.domain.SeatHold;
import com.wallmart.tiket.repo.BookingDao;
import com.wallmart.tiket.repo.TicketServiceDao;
import com.wallmart.tiket.utils.Constants;

/**
 *  Seats are numbered 1,2,3....33 etc for row 1
 *  				   34,35 ..  for row 2
 * 
 * @author Anish Jose
 * 
 */

@Service("ticketService")
public class TicketServiceImpl implements TicketService,Constants {

	@Autowired
	BookingDao bDao;
	
	@Autowired
	TicketServiceDao tDao;
	
	@Value("${facility.seats.rows:9}")
	private int totalRows;
	
	@Value("${facility.seats.columns:33}")
	private int totalColumns;
	private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
	
	public int numSeatsAvailable(long eventId) {
		// TODO Auto-generated method stub	
		int totalSeats = totalRows*totalColumns;		
		return (totalSeats- (tDao.getReservedSeats(eventId)));
	}
/***
 *  Find and Hold seat based on availability. Below method will find out free seats and hold for the customer.
 */
@Transactional
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail, long eventId) {
		// TODO Auto-generated method stub
		
		Bookings booking =new Bookings();
		
		booking.setEmail(customerEmail);
		booking.setEventId(eventId);
		booking.setTime(new Date().getTime());
		booking.setNumberOfSeats(numSeats);
		booking.setStatus(HOLD);
		String seats="";
		SeatHold sh= new SeatHold();
		List<Integer> location = findOutFreeSeats(eventId,numSeats);
		boolean status =false;
		if(location.size()!=numSeats)
		{
			sh.setBookingStatus(status);
			logger.info("Unable to get requested number of seats");
			return sh;
		}
		Iterator<Integer> itr= location.iterator();	
		while(itr.hasNext())
		{
			seats=seats+itr.next()+",";
		}
		seats = seats.substring(0, seats.length() - 1); //remove last ","
		booking.setSeats(seats);		
		sh.setCustomerEmail(customerEmail);
		sh.setNumSeats(numSeats);
		
		try {
			bDao.save(booking);	
			status =true;
			sh.setEventId(eventId);
			sh.setSeatHoldId(booking.getTime());
		}catch(Exception e)
		{
			logger.info("Booking failed :: Error message::"+e.getMessage());
		}
		sh.setBookingStatus(status);
		return sh;
	}
/**
 * Reserving seats based on seatHoldId, status is getting changed
 * 
 */
	public Message reserveSeats(long seatHoldId, String customerEmail,long eventId) {
		// TODO Auto-generated method stub
	
		boolean status= tDao.reserveSeats(seatHoldId,customerEmail,eventId);
		logger.info("Seat reservation Status::"+status);
		Message msg= new Message();
		msg.setStatus(status);
		if(status)
		{
			msg.setMessage("Seat reservation successfull");
		}else {		
			msg.setMessage("Seat reservation failed");
		}
		 return msg;
	}

	
/**
 * displaySeats will display seat allocation graphically	
 * @param eventId
 * @return
 */
	public List<String> displaySeats(long eventId)
	{
		List<String> front =new ArrayList<String>();
		List<String> display =new ArrayList<String>();	
		front.add(STAGE);
		front.add(FRONT_RAW);
		String s ="";
		for (int i=0;i<totalColumns;i++)
		{
			s=s+S;
		}
		for(int i=0;i<totalRows;i++)
		{
			display.add(s);
		}
		List<Integer> reserved= tDao.getReservedSeatsForAnEvent(eventId);
		// Seats are numbered 1 to 297..
		int total=totalRows*totalColumns;
		Iterator<Integer> itr= reserved.iterator();		
		while (itr.hasNext())
		{
			int temp= itr.next();
			int row = temp / totalColumns;
			int column = temp % totalColumns;
			char [] array= display.get(row).toCharArray();
			array[column-1] = R;
			display.set(row,String.copyValueOf(array));
		}
		List<Integer> holded= tDao.getHoldedSeatsForAnEvent(eventId);
		// Seats are numbered 1 to 297..		
		itr= holded.iterator();		
		while (itr.hasNext())
		{
			int temp= itr.next();
			int row = temp / totalColumns;
			int column = temp % totalColumns;
			char [] array= display.get(row).toCharArray();
			array[column-1] = H;
			display.set(row,String.copyValueOf(array));
		}
		Iterator<String> it= display.iterator();
		while (it.hasNext())
		{
			front.add(it.next());
		}
		return front;
		
	}
/**
 * 	Below method to find out free spaces based on number of seats requested.
 * @param eventId
 * @param numSeats
 * @return
 */
	private List<Integer> findOutFreeSeats(long eventId,int numSeats)
	{
		List<String> seats = displaySeats(eventId);		
		List<String> temp = new ArrayList<String>();		
		List<Integer> locations =new ArrayList<Integer>();
		
		for(int i=2;i<seats.size();i++)
		{
			temp.add(seats.get(i));
		}
		
		Iterator<String> it= temp.iterator();
		int loc=0;
		while (it.hasNext() && numSeats >0)
		{
			char [] array= it.next().toCharArray();			
			for(int i=0;i<array.length;i++)
			{
				loc++;
				if(array[i] == S)
				{
					locations.add(loc);
					numSeats--;
				}
				if(numSeats==0)
				{
					break;
				}
			}
		}
		
		return locations;
		
	}

}
