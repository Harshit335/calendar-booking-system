package com.harshitjain.calendar_booking_system.repository;

import com.harshitjain.calendar_booking_system.model.CalendarAvailability;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryAvailabilityRepository implements AvailabilityRepository {

    private final Map<String, CalendarAvailability> availabilityMap = new HashMap<>();

    @Override
    public void save(CalendarAvailability availability) {
        availabilityMap.put(availability.getOwnerEmail(), availability);
    }

    @Override
    public CalendarAvailability findByOwner(String ownerEmail) {
        return availabilityMap.get(ownerEmail);
    }
}