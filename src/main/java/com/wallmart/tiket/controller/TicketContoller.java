package com.wallmart.tiket.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.wallmart.tiket.domain.Facility;
import com.wallmart.tiket.domain.Message;
import com.wallmart.tiket.domain.SeatHold;
import com.wallmart.tiket.service.FacilityService;
import com.wallmart.tiket.service.TicketService;

@RestController
public class TicketContoller {

	@Autowired
	FacilityService fService;
	@Autowired 
	TicketService tService;
	private static final Logger logger = LoggerFactory.getLogger(TicketContoller.class);
		
	@RequestMapping(value = "getAvailableSeats" , method = RequestMethod.GET) 
	 public  @ResponseBody int getAvailableSeats(@RequestParam("eventId") long eventId)
   {
			
		return tService.numSeatsAvailable(eventId); 		
   }
	@RequestMapping(value = "displaySeats", method = RequestMethod.GET) 
	 public  @ResponseBody List displaySeats(@RequestParam("eventId") long eventId)
	{
		return tService.displaySeats(eventId);
	 }
	@RequestMapping(value = "createEvent" , method = RequestMethod.GET)
	public  @ResponseBody boolean createEvent(@RequestParam("eventName") String eventName )
	{
		
		return fService.createEvent(eventName);
		
	}
	
	@RequestMapping(value = "getEvents" , method = RequestMethod.GET)
	public  @ResponseBody List<Facility> getEvents()
	{
		return fService.getEvents();
	}
	
	@RequestMapping(value = "findAndHoldSeats" , method = RequestMethod.GET)
	public  @ResponseBody SeatHold findAndHoldSeats(@RequestParam("eventId") long eventId, @RequestParam("numberOfSeats") int numSeats,@RequestParam("email") String customerEmail)
	{
		SeatHold status =tService.findAndHoldSeats(numSeats, customerEmail, eventId);	
		return status;
	}
	@RequestMapping(value = "reserveSeats" , method = RequestMethod.GET)
	public  @ResponseBody Message reserveSeats(@RequestParam("eventId") long eventId, @RequestParam("seatHoldId") long seatHoldId,@RequestParam("email") String customerEmail)
	{
		
		return tService.reserveSeats(seatHoldId, customerEmail, eventId);
	}
	
}
