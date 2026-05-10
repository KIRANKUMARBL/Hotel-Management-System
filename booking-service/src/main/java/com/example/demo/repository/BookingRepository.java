package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Booking;
import java.util.*;
public interface BookingRepository extends JpaRepository<Booking, Integer>{
	List<Booking> findAllByOrderByIdDesc();
}
