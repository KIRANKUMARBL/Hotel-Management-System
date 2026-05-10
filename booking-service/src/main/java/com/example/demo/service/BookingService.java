package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Guest;
import com.example.demo.feign.PaymentClient;
import com.example.demo.feign.RoomClient;
import com.example.demo.model.BookingRequest;
import com.example.demo.model.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.GuestRepository;

@Service
public class BookingService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomClient roomClient;

    @Autowired
    private PaymentClient paymentClient;
    

    @Async
    public CompletableFuture<Map<String, Object>> createBooking(BookingRequest request) {

        // ✅ Validate request
        if (request == null ||
            request.getGuest() == null ||
            request.getBooking() == null) {

            throw new RuntimeException("Invalid booking request");
        }

        // ✅ Save guest
        Guest savedGuest = guestRepository.save(request.getGuest());

        // ✅ Prepare booking
        Booking booking = request.getBooking();
        booking.setGuestId(savedGuest.getId());

        // ✅ Fetch room
        Room room = roomClient.getRoomById(booking.getRoomId());

        if (room == null) {
            throw new RuntimeException("Room not found$");
        }

        // ✅ Room already booked
        if (!room.isAvailable()) {
            throw new RuntimeException("Room not available");
        }

        // ✅ Mark room unavailable
        room.setAvailable(false);
        roomClient.updateRoom(room.getId(), room);

        // ✅ Save booking
        booking.setStatus("CONFIRMED");

        Booking savedBooking = bookingRepository.save(booking);

        // ✅ OPTIONAL PAYMENT
		/*
		 * Map<String, Object> paymentRequest = new HashMap<>();
		 * paymentRequest.put("bookingId", savedBooking.getId());
		 * paymentRequest.put("amount", room.getPrice());
		 * 
		 * paymentClient.makePayment(paymentRequest);
		 */
        

        // ✅ Response
        Map<String, Object> response = new HashMap<>();
        response.put("booking", savedBooking);
        response.put("guest", savedGuest);

        return CompletableFuture.completedFuture(response);
    }
    
    public Booking cancelBooking(int bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found4"));

        // ✅ update status
        booking.setStatus("CANCELLED");

        // ✅ FREE the room
        Room room = roomClient.getRoomById(booking.getRoomId());

        if (room != null) {
            room.setAvailable(true);
            roomClient.updateRoom(room.getId(), room);
        }

        return bookingRepository.save(booking);
    }
}