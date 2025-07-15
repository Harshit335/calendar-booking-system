package com.harshitjain.calendar_booking_system.Service.Implementation;

import com.harshitjain.calendar_booking_system.DTOs.CalendarAvailabilityRequest;
import com.harshitjain.calendar_booking_system.model.CalendarAvailability;
import com.harshitjain.calendar_booking_system.Service.AvailabilityService;
import com.harshitjain.calendar_booking_system.exception.CalendarErrors;
import com.harshitjain.calendar_booking_system.exception.CalendarServiceException;
import com.harshitjain.calendar_booking_system.repository.AvailabilityRepository;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public void setAvailability(String ownerEmail, CalendarAvailabilityRequest request) {
        LocalTime start, end;

        try {
            start = LocalTime.parse(request.getStartTime());
            end = LocalTime.parse(request.getEndTime());
        } catch (DateTimeParseException ex) {
            throw new CalendarServiceException(CalendarErrors.TIME_FORMAT_INVALID);
        }

        if (start.isAfter(end) || start.equals(end)) {
            throw new CalendarServiceException(CalendarErrors.INVALID_TIME_RANGE);
        }

        if (start.getMinute() != 0 || end.getMinute() != 0) {
            throw new CalendarServiceException(CalendarErrors.TIME_FORMAT_INVALID);
        }

        CalendarAvailability availability = new CalendarAvailability(ownerEmail, start, end);
        availabilityRepository.save(availability);
    }

    @Override
    public CalendarAvailability getAvailability(String ownerEmail) {
        return availabilityRepository.findByOwner(ownerEmail);
    }
}
