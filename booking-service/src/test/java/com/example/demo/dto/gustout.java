package com.example.demo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GuestDTOTest {

    @Test
    void testGuestDTOGettersAndSetters() {
        GuestDTO guest = new GuestDTO();

        guest.setId(1);
        guest.setName("John");
        guest.setEmail("john@example.com");
        guest.setPhone("9876543210");

        assertEquals(1, guest.getId());
        assertEquals("John", guest.getName());
        assertEquals("john@example.com", guest.getEmail());
        assertEquals("9876543210", guest.getPhone());
    }
}