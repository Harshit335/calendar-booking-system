package com.harshitjain.calendar_booking_system.service;

import com.harshitjain.calendar_booking_system.DTOs.*;
import com.harshitjain.calendar_booking_system.Service.*;
import com.harshitjain.calendar_booking_system.Service.Implementation.*;
import com.harshitjain.calendar_booking_system.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentQueryServiceTest {

    private AppointmentQueryService appointmentQueryService;
    private BookingService bookingService;
    private AvailabilityService availabilityService;

    private final String OWNER_EMAIL = "harshit@mail.com";
    private final String INVITEE_EMAIL = "rajat@mail.com";

    @BeforeEach
    void setup() {
        // ✅ Use real in-memory repositories to share data across services
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        AvailabilityRepository availabilityRepository = new InMemoryAvailabilityRepository();

        availabilityService = new AvailabilityServiceImpl(availabilityRepository);
        appointmentQueryService = new AppointmentQueryServiceImpl(appointmentRepository);
        bookingService = new BookingServiceImpl(availabilityService, appointmentRepository);
    }

    @Test
    @DisplayName("Get upcoming appointments only")
    void testGetAllUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        LocalDate future = today.plusDays(1);
        LocalTime current = LocalTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);

        availabilityService.setAvailability(OWNER_EMAIL, new CalendarAvailabilityRequest("00:00", "23:00"));

        bookingService.bookAppointment(OWNER_EMAIL,
                new AppointmentRequest(current.toString(), today.toString(), "Today", INVITEE_EMAIL, "Test"));

        bookingService.bookAppointment(OWNER_EMAIL,
                new AppointmentRequest("12:00", future.toString(), "Future", INVITEE_EMAIL, "Test"));

        List<AppointmentResponse> result = appointmentQueryService.getAllAppointments(OWNER_EMAIL);

        assertThat(result).hasSize(2);
        assertThat(result.stream().anyMatch(r -> r.getDate().equals(today.toString()))).isTrue();
        assertThat(result.stream().anyMatch(r -> r.getDate().equals(future.toString()))).isTrue();
    }

    @Test
    @DisplayName("Get appointments by a specific date")
    void testGetAppointmentsByDate() {
        LocalDate today = LocalDate.now();
        LocalDate anotherDay = today.plusDays(2);

        availabilityService.setAvailability(OWNER_EMAIL, new CalendarAvailabilityRequest("00:00", "23:00"));

        bookingService.bookAppointment(OWNER_EMAIL,
                new AppointmentRequest("10:00", today.toString(), "User", INVITEE_EMAIL, "Demo"));
        bookingService.bookAppointment(OWNER_EMAIL,
                new AppointmentRequest("11:00", anotherDay.toString(), "User", INVITEE_EMAIL, "Pitch"));

        List<AppointmentResponse> result = appointmentQueryService.getAppointmentsByDate(OWNER_EMAIL, today);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Demo");
    }
}
