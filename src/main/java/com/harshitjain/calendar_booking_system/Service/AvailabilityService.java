package com.harshitjain.calendar_booking_system.Service;

import com.harshitjain.calendar_booking_system.DTOs.CalendarAvailabilityRequest;
import com.harshitjain.calendar_booking_system.model.CalendarAvailability;

public interface AvailabilityService {
    void setAvailability(String ownerEmail, CalendarAvailabilityRequest request);
    CalendarAvailability getAvailability(String ownerEmail);
}