package com.wallmart.tiket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wallmart.tiket.domain.Facility;
import com.wallmart.tiket.repo.FacilityDao;
/**
 * Facility Searvice to create new events, handling all event related details
 * @author Anish Jose
 *
 */
@Service("facilityService")
public class FacilityServiceImpl implements FacilityService {

	@Value("${facility.seats.rows:9}")
	private int totalRows;
	
	@Value("${facility.seats.columns:9}")
	private int totalColumns;
	
	@Autowired
	private FacilityDao fDao;
	@Override
	public boolean createEvent(String eventName) {

		Facility f=new Facility();
		f.setEventName(eventName);
		f.setTotalColumns(totalColumns);
		f.setTotalRows(totalRows);
		Date d= new Date();	
		Long l= new Long(d.getTime());
		f.setId(l);
		fDao.save(f);
		return false;
	}
	@Override
	public List<Facility> getEvents() {

		Iterable<Facility> ite=  fDao.findAll();		
		List<Facility> facility= new ArrayList();
		ite.forEach(fac->facility.add(fac));		
		return facility;
	}

}
