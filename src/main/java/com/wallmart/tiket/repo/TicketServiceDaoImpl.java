package com.wallmart.tiket.repo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.wallmart.tiket.domain.Facility;
import com.wallmart.tiket.domain.Seat;
import com.wallmart.tiket.utils.Constants;
/***
 * TicketServiceDaoImpl handles all ticket related operation on H2 DB
 * @author Anish Jose
 *
 */
@Repository
public class TicketServiceDaoImpl implements TicketServiceDao,Constants {

	@PersistenceContext
    private EntityManager entityManager;	

	@Value("${hold.ttl:10000}")
	private long ttl;
	private static final Logger logger = LoggerFactory.getLogger(TicketServiceDaoImpl.class);
/**
 * To find the total number of seats available	
 */
	public int getReservedSeats(long eventId)
	{
		Query q = entityManager.createNativeQuery(FIND_SUM).setParameter(1, eventId);	
		int seats = (int) ((q.getSingleResult()!=null)?q.getSingleResult():0);
		return seats;
	}
/***
 * Seats are reserverd with 1,2,3,...etc	
 * @param eventId
 * @return
 */
	public List<Integer> getReservedSeatsForAnEvent(long eventId)
	{
		List<Integer> reservation= new ArrayList<Integer>();
		Query q = entityManager.createNativeQuery(FIND_RESERVE).setParameter(1, eventId); 
		List<Object[]> results=q.getResultList();
		
		results.stream().forEach((record) -> {

	        String seats = (String) record[0];
	        Long time = ((BigInteger) (record[1])).longValue();
	        if(seats!=null )
	        {
	        	String[] result = seats.split(",");  // Assuming that seats are allocated by comma seperated
	        	for(int i=0;i<result.length;i++)
	        	{
	        		try	
	        		{
	        				reservation.add(Integer.parseInt((result[i]!=null)?result[i]:"0")); 
	        		}catch(Exception e)
	        		{
	        			
	        		}
	        	}
	        	
	        }
	});
		Collections.sort(reservation);
		
		return reservation;
	}
/***
 *  Finding out seats on hold
 */
	public List<Integer> getHoldedSeatsForAnEvent(long eventId)
	{
		List<Integer> reservation= new ArrayList<Integer>();
		Query q = entityManager.createNativeQuery(FIND_HOLD).setParameter(1, eventId); 
		List<Object[]> results=q.getResultList();
		
		results.stream().forEach((record) -> {

	        String seats = (String) record[0];
	        Long time = ((BigInteger) (record[1])).longValue();
	        if(seats!=null )
	        {
	        	String[] result = seats.split(",");  // Assuming that seats are allocated by comma seperated
	        	for(int i=0;i<result.length;i++)
	        	{
	        		try	
	        		{
	        				reservation.add(Integer.parseInt((result[i]!=null)?result[i]:"0")); 
	        		}catch(Exception e)
	        		{
	        			
	        		}
	        	}
	        	
	        }
	});
		Collections.sort(reservation);
		
		return reservation;
	}
/***
 *  Reserve seats based on seatHoldId	
 */
@Override
@Transactional
public boolean reserveSeats(long seatHoldId, String email, long eventId) {

	Query q = entityManager.createNativeQuery(RESERVE_SEAT).setParameter(1, eventId).setParameter(2,seatHoldId).setParameter(3,email); 
	int result= q.executeUpdate();
	if(result>0)
	{	return true;
	}			
	return false;
}
/**
 *  Private method to get seat level details while freeing up seats after TTL 
 * @param eventId
 * @return
 */
private List<Seat> holdedSeats(long eventId)
{
	List<Seat> seats =new ArrayList<Seat>();
	Query q = entityManager.createNativeQuery(HOLD_SEAT).setParameter(1, eventId); 
	List<Object[]> results=q.getResultList();	
	results.stream().forEach((record) -> {
       Seat seat= new Seat();
       seat.setEventId(eventId);
       seat.setSeatHoldId(((BigInteger) (record[0])).longValue());
       seat.setEmail((String) record[1]);
       seats.add(seat);
	});
	return seats;
}
/***
 * Soft delete for holded seats after TTL expired
 */
@Override
@Transactional
public void findOutAndReleaseSeatsOnHold(List<Facility> events) {

	Iterator<Facility> itr =events.iterator();	
	List<Seat> free = new ArrayList<Seat>();	
	while(itr.hasNext())
	{		
		Facility facility= itr.next();
		long eventId= facility.getId();
		long now= new Date().getTime();
		List<Seat> holded= holdedSeats(eventId);
		
		for(int i=0;i<holded.size();i++)
		{
			Seat seat= holded.get(i);			
			logger.info(" TTL difference::"+(now-seat.getSeatHoldId()));			
			if(now-seat.getSeatHoldId() >ttl) // Holded for long time, free up now
			{
				free.add(seat);
			}
		}
		Iterator<Seat> it =free.iterator();		
		while(it.hasNext())
		{
			Seat seat= it.next();
			Query q = entityManager.createNativeQuery(SOFT_DELETE).setParameter(1, seat.getEventId()).setParameter(2,seat.getSeatHoldId()).setParameter(3,seat.getEmail()); 
			int result= q.executeUpdate();
/*			try {
			CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();			
			CriteriaDelete<Bookings> criteriaDelete = criteriaBuilder.createCriteriaDelete(Bookings.class);
			Root<Bookings> root = criteriaDelete.from(Bookings.class);
			Predicate p1 = criteriaBuilder.equal(root.get("TIME"), seat.getSeatHoldId());
			Predicate p2 = criteriaBuilder.equal(root.get("EVENT_ID"), seat.getEventId());
			Predicate p3 = criteriaBuilder.equal(root.get("EMAIL"), seat.getEmail());			
		    entityManager.createQuery(criteriaDelete.where(criteriaBuilder.and(p1,p2,p3))).executeUpdate();
			}
			catch(Exception e)
			{
				logger.info(e.getMessage());
			}*/
		}
		
	}
	
	
}



}
