package com.harshitjain.calendar_booking_system.service;

import com.harshitjain.calendar_booking_system.DTOs.*;
import com.harshitjain.calendar_booking_system.Service.*;
import com.harshitjain.calendar_booking_system.Service.Implementation.*;
import com.harshitjain.calendar_booking_system.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SlotServiceTest {

    private SlotService slotService;
    private BookingService bookingService;
    private AvailabilityService availabilityService;

    private final String OWNER_EMAIL = "harshit@mail.com";
    private final String INVITEE_EMAIL = "rajat@mail.com";
    private final LocalDate TODAY = LocalDate.now();

    @BeforeEach
    void setup() {
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        AvailabilityRepository availabilityRepository = new InMemoryAvailabilityRepository();

        availabilityService = new AvailabilityServiceImpl(availabilityRepository);
        bookingService = new BookingServiceImpl(availabilityService, appointmentRepository);
        slotService = new SlotServiceImpl(availabilityService, appointmentRepository);
    }

    @Test
    @DisplayName("Slot availability decreases after booking")
    void testAvailableSlotCountAfterBooking() {
        availabilityService.setAvailability(OWNER_EMAIL, new CalendarAvailabilityRequest("10:00", "12:00"));

        // Book 10:00-11:00
        bookingService.bookAppointment(
                OWNER_EMAIL,
                new AppointmentRequest("10:00", TODAY.toString(), "Rajat", INVITEE_EMAIL, "Test")
        );

        List<AvailableSlots> slots = slotService.getAvailableSlots(OWNER_EMAIL, TODAY);

        assertThat(slots).hasSize(1);
        assertThat(slots.get(0).getStartTime()).isEqualTo("11:00");
        assertThat(slots.get(0).getEndTime()).isEqualTo("12:00");
    }
}
