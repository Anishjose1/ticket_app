package com.wallmart.tiket.service;

import java.util.List;

import com.wallmart.tiket.domain.Facility;

public interface FacilityService {

	public boolean createEvent(String eventName);
	
	public List<Facility> getEvents();
}
