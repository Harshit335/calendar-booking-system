package com.harshitjain.calendar_booking_system.service;

import com.harshitjain.calendar_booking_system.DTOs.*;
import com.harshitjain.calendar_booking_system.exception.CalendarErrors;
import com.harshitjain.calendar_booking_system.exception.CalendarServiceException;
import com.harshitjain.calendar_booking_system.repository.*;
import com.harshitjain.calendar_booking_system.Service.*;
import com.harshitjain.calendar_booking_system.Service.Implementation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingServiceTest {

    private BookingService bookingService;
    private AvailabilityService availabilityService;

    private static final LocalDate LOCAL_DATE = LocalDate.now();
    private static final String DATE = LOCAL_DATE.toString();

    private final String OWNER_EMAIL = "harshit@mail.com";
    private final String INVITEE_EMAIL = "rajat@mail.com";

    @BeforeEach
    void setup() {
        // ✅ Use real in-memory repositories to share data across services
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        AvailabilityRepository availabilityRepository = new InMemoryAvailabilityRepository();

        availabilityService = new AvailabilityServiceImpl(availabilityRepository);
        bookingService = new BookingServiceImpl(availabilityService, appointmentRepository);
    }

    @DisplayName("Booking Scenarios: valid, conflict, past, format, overlap")
    @ParameterizedTest(name = "Booking: start={0}, date={1}, expectSuccess={2}, expectedMsg={3}")
    @CsvSource({
            "10:00, 2025-08-15, true, ''",
            "10:30, 2025-08-15, false, Time must be in HH:00 format",
            "11:10, 2025-08-15, false, Time must be in HH:00 format",
            "12:00, 2025-08-15, false, Start time must be before end time",
            "10:00, 2025-08-15, false, Requested slot is already booked",
            "10:00, 2020-01-01, false, Cannot book an appointment in the past"
    })
    void testBookingScenarios(String startTime, String date, boolean expectSuccess, String expectedMsg) {
        availabilityService.setAvailability(OWNER_EMAIL, new CalendarAvailabilityRequest("10:00", "12:00"));

        if (startTime.equals("10:00") && !expectSuccess && expectedMsg.equals("Requested slot is already booked")) {
            bookingService.bookAppointment(OWNER_EMAIL, new AppointmentRequest(startTime, date, "Rajat", INVITEE_EMAIL, "Already Booked"));
        }

        AppointmentRequest request = new AppointmentRequest(startTime, date, "User", "user@email.com", "Test");

        if (expectSuccess) {
            assertThatCode(() -> bookingService.bookAppointment(OWNER_EMAIL, request)).doesNotThrowAnyException();
        } else {
            CalendarServiceException ex = assertThrows(CalendarServiceException.class, () -> bookingService.bookAppointment(OWNER_EMAIL, request));
            assertThat(ex.getMessage()).isEqualTo(expectedMsg);
        }
    }

    @Test
    @DisplayName("Booking fails if availability is not set")
    void testBookingWithoutAvailabilityThrows() {
        AppointmentRequest request = new AppointmentRequest("10:00", DATE, "Rajat", INVITEE_EMAIL, "Fail");
        CalendarServiceException ex = assertThrows(CalendarServiceException.class, () -> bookingService.bookAppointment(OWNER_EMAIL, request));
        assertThat(ex.getType()).isEqualTo(CalendarErrors.AVAILABILITY_NOT_SET);
    }
}

