package com.example.demo.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Guest;
import com.example.demo.model.BookingRequest;
import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.GuestDTO;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    // ✅ ADD GUEST
    @PostMapping("/guest")
    public Guest addGuest(@RequestBody Guest guest) {
        return guestRepository.save(guest);
    }

    // ✅ CREATE BOOKING
    @PostMapping("/create")
    public CompletableFuture<Map<String, Object>> createBooking(
            @Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    // ✅ GET ALL BOOKINGS (WITH GUEST DATA)
    @GetMapping("/all")
    public List<BookingDTO> getAllBookings() {

        return bookingRepository.findAllByOrderByIdDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET SINGLE BOOKING (IMPORTANT FOR DETAILS PAGE)
    @GetMapping("/{id}")
    public BookingDTO getBookingById(@PathVariable int id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found1"));

        return convertToDTO(booking);
    }

    // ✅ DELETE BOOKING
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable int id) {
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }

    // ✅ UPDATE BOOKING STATUS ONLY
    @PutMapping("/update/{id}")
    public BookingDTO updateBooking(@PathVariable int id, @RequestBody BookingDTO dto) {

        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found2"));

        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        Booking saved = bookingRepository.save(existing);

        return convertToDTO(saved);
    }

    // ✅ UPDATE GUEST
    @PutMapping("/guest/{id}")
    public Guest updateGuest(@PathVariable int id, @RequestBody Guest guest) {

        Guest existing = guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found3"));

        existing.setName(guest.getName());
        existing.setEmail(guest.getEmail());
        existing.setPhone(guest.getPhone());
        existing.setAddress(guest.getAddress());
        existing.setGender(guest.getGender());

        return guestRepository.save(existing);
    }

    // ✅ DELETE GUEST
    @DeleteMapping("/guest/{id}")
    public String deleteGuest(@PathVariable int id) {
        guestRepository.deleteById(id);
        return "Guest deleted successfully";
    }

    // ✅ CANCEL BOOKING
    @PutMapping("/cancel/{id}")
    public BookingDTO cancelBooking(@PathVariable int id) {

        Booking cancelled = bookingService.cancelBooking(id);

        return convertToDTO(cancelled);
    }

    // 🔥 CENTRALIZED DTO CONVERTER (VERY IMPORTANT)
    private BookingDTO convertToDTO(Booking b) {

        BookingDTO dto = new BookingDTO();
        dto.setBookingId(b.getId());
        dto.setRoomId(b.getRoomId());
        dto.setStatus(b.getStatus());

        // fetch guest
        if (b.getGuestId() >0) {
            guestRepository.findById(b.getGuestId()).ifPresent(g -> {
                GuestDTO guestDTO = new GuestDTO();
                guestDTO.setId(g.getId());
                guestDTO.setName(g.getName());
                guestDTO.setEmail(g.getEmail());
                guestDTO.setPhone(g.getPhone());

                dto.setGuest(guestDTO);
            });
        }

        return dto;
    } 
}