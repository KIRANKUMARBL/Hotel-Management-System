package com.example.demo.controller;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Guest;
import com.example.demo.model.BookingRequest;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private GuestRepository guestRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddGuest() throws Exception {

        Guest guest = new Guest();
        guest.setId(1);
        guest.setName("Kiran");
        guest.setEmail("kiran@gmail.com");

        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        mockMvc.perform(post("/booking/guest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kiran"));
    }

    @Test
    void testCreateBooking() throws Exception {

        BookingRequest request = new BookingRequest();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Booking created");

        when(bookingService.createBooking(any(BookingRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        mockMvc.perform(post("/booking/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllBookings() throws Exception {

        Booking booking = new Booking();
        booking.setId(1);
        booking.setRoomId(101);
        booking.setStatus("BOOKED");
        booking.setGuestId(1);

        Guest guest = new Guest();
        guest.setId(1);
        guest.setName("Kiran");

        when(bookingRepository.findAllByOrderByIdDesc())
                .thenReturn(List.of(booking));

        when(guestRepository.findById(1))
                .thenReturn(Optional.of(guest));

        mockMvc.perform(get("/booking/all"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookingById() throws Exception {

        Booking booking = new Booking();
        booking.setId(1);
        booking.setRoomId(101);
        booking.setStatus("BOOKED");
        booking.setGuestId(1);

        Guest guest = new Guest();
        guest.setId(1);
        guest.setName("Kiran");

        when(bookingRepository.findById(1))
                .thenReturn(Optional.of(booking));

        when(guestRepository.findById(1))
                .thenReturn(Optional.of(guest));

        mockMvc.perform(get("/booking/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1));
    }

    @Test
    void testDeleteBooking() throws Exception {

        mockMvc.perform(delete("/booking/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking deleted successfully"));
    }

    @Test
    void testUpdateBooking() throws Exception {

        Booking booking = new Booking();
        booking.setId(1);
        booking.setRoomId(101);
        booking.setStatus("BOOKED");
        booking.setGuestId(1);

        Guest guest = new Guest();
        guest.setId(1);
        guest.setName("Kiran");

        BookingDTO dto = new BookingDTO();
        dto.setStatus("CANCELLED");

        when(bookingRepository.findById(1))
                .thenReturn(Optional.of(booking));

        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(booking);

        when(guestRepository.findById(1))
                .thenReturn(Optional.of(guest));

        mockMvc.perform(put("/booking/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateGuest() throws Exception {

        Guest guest = new Guest();
        guest.setId(1);
        guest.setName("Old");

        Guest updated = new Guest();
        updated.setName("New");

        when(guestRepository.findById(1))
                .thenReturn(Optional.of(guest));

        when(guestRepository.save(any(Guest.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/booking/guest/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteGuest() throws Exception {

        mockMvc.perform(delete("/booking/guest/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Guest deleted successfully"));
    }

    @Test
    void testCancelBooking() throws Exception {

        Booking booking = new Booking();
        booking.setId(1);
        booking.setRoomId(101);
        booking.setStatus("CANCELLED");
        booking.setGuestId(1);

        Guest guest = new Guest();
        guest.setId(1);
        guest.setName("Kiran");

        when(bookingService.cancelBooking(1))
                .thenReturn(booking);

        when(guestRepository.findById(1))
                .thenReturn(Optional.of(guest));

        mockMvc.perform(put("/booking/cancel/1"))
                .andExpect(status().isOk());
    }
}