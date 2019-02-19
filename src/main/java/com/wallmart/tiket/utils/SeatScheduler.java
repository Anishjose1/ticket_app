package com.wallmart.tiket.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.wallmart.tiket.domain.Facility;
import com.wallmart.tiket.repo.TicketServiceDao;
import com.wallmart.tiket.service.FacilityService;
/**
 * Scheduler to freeup unused seats which are expired after TTL
 * @author Anish Jose
 *
 */
@Component
public class SeatScheduler {

	@Autowired
	FacilityService fService;
	@Autowired
	TicketServiceDao tDao;	
	private static final Logger logger = LoggerFactory.getLogger(SeatScheduler.class);
	
	 @Scheduled(fixedRate = 600000)
	 public void freeupHoldedSeats()
	 {
		 List<Facility> events= fService.getEvents();	
		 logger.info("Scheduler call ::::");
		 tDao.findOutAndReleaseSeatsOnHold(events);		 
		 
	 }
}
