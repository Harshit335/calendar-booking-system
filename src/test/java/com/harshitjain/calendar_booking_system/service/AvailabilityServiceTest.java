package com.harshitjain.calendar_booking_system.service;

import com.harshitjain.calendar_booking_system.DTOs.CalendarAvailabilityRequest;
import com.harshitjain.calendar_booking_system.exception.CalendarServiceException;
import com.harshitjain.calendar_booking_system.Service.AvailabilityService;
import com.harshitjain.calendar_booking_system.Service.Implementation.AvailabilityServiceImpl;
import com.harshitjain.calendar_booking_system.repository.AvailabilityRepository;
import com.harshitjain.calendar_booking_system.repository.InMemoryAvailabilityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AvailabilityServiceTest {

    private AvailabilityService availabilityService;
    
    private final String OWNER_EMAIL = "harshit@mail.com";

    @BeforeEach
    public void setup() {
        AvailabilityRepository availabilityRepository = new InMemoryAvailabilityRepository();
        availabilityService = new AvailabilityServiceImpl(availabilityRepository);
    }

    @DisplayName("Availability Validation - only full hour slots and valid time range")
    @ParameterizedTest(name = "Availability: start={0}, end={1}, expectedMsg={2}")
    @CsvSource({
            "10:00, '', Time must be in HH:00 format",
            "10:00, 10:00, Start time must be before end time",
            "10:00, 11:10, Time must be in HH:00 format",
            "10:00, 11:000, Time must be in HH:00 format",
            "10:00, 09:00, Start time must be before end time",
            "10:00, 11:00, "
    })
    public void testSetAvailability_validOnlyFullHourSlots(String startTime, String endTime, String expectedMsg) {
        CalendarAvailabilityRequest request = new CalendarAvailabilityRequest(startTime, endTime);

        boolean isValid = isStrictHourFormat(startTime)
                && isStrictHourFormat(endTime)
                && startTime.compareTo(endTime) < 0;

        if (isValid) {
            assertThatCode(() -> availabilityService.setAvailability(OWNER_EMAIL, request)).doesNotThrowAnyException();
        } else {
            CalendarServiceException ex = assertThrows(CalendarServiceException.class,
                    () -> availabilityService.setAvailability(OWNER_EMAIL, request));
            assertThat(ex.getMessage()).isEqualTo(expectedMsg);
        }
    }

    private boolean isStrictHourFormat(String time) {
        try {
            LocalTime parsed = LocalTime.parse(time);
            return parsed.getMinute() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
