package com.harshitjain.calendar_booking_system.repository;

import com.harshitjain.calendar_booking_system.model.CalendarAvailability;

public interface AvailabilityRepository {
    void save(CalendarAvailability availability);
    CalendarAvailability findByOwner(String ownerEmail);
}
