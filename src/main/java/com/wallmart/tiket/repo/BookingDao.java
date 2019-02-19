package com.wallmart.tiket.repo;

import org.springframework.data.repository.CrudRepository;

import com.wallmart.tiket.domain.Bookings;

public interface BookingDao extends CrudRepository<Bookings, Long> {

}
