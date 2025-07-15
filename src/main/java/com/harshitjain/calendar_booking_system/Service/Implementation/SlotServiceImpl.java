package com.harshitjain.calendar_booking_system.Service.Implementation;

import com.harshitjain.calendar_booking_system.DTOs.AvailableSlots;
import com.harshitjain.calendar_booking_system.model.Appointment;
import com.harshitjain.calendar_booking_system.model.CalendarAvailability;
import com.harshitjain.calendar_booking_system.model.TimeInterval;
import com.harshitjain.calendar_booking_system.Service.AvailabilityService;
import com.harshitjain.calendar_booking_system.Service.SlotService;
import com.harshitjain.calendar_booking_system.exception.CalendarErrors;
import com.harshitjain.calendar_booking_system.exception.CalendarServiceException;
import com.harshitjain.calendar_booking_system.repository.AppointmentRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotServiceImpl implements SlotService {

    private final AvailabilityService availabilityService;
    private final AppointmentRepository appointmentRepository;
    private static final int SLOT_DURATION_MINUTES = 60;

    public SlotServiceImpl(AvailabilityService availabilityService, AppointmentRepository appointmentRepository) {
        this.availabilityService = availabilityService;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AvailableSlots> getAvailableSlots(String ownerEmail, LocalDate date) {
        CalendarAvailability calendarAvailability = availabilityService.getAvailability(ownerEmail);
        if (calendarAvailability == null) {
            throw new CalendarServiceException(CalendarErrors.AVAILABILITY_NOT_SET);
        }

        List<Appointment> bookedOnDate = appointmentRepository.findByOwnerAndDate(ownerEmail, date);

        List<AvailableSlots> availableSlots = new ArrayList<>();
        List<TimeInterval> slots = generateSlots(calendarAvailability.getStartTime(), calendarAvailability.getEndTime());

        for (TimeInterval slot : slots) {
            boolean isOverlapping = bookedOnDate.stream().anyMatch(app ->
                    app.getStartTime().isBefore(slot.getEnd()) && app.getEndTime().isAfter(slot.getStart()));

            if (!isOverlapping) {
                availableSlots.add(new AvailableSlots(slot.getStart().toString(), slot.getEnd().toString()));
            }
        }
        return availableSlots;
    }

    private List<TimeInterval> generateSlots(LocalTime start, LocalTime end) {
        List<TimeInterval> slots = new ArrayList<>();
        LocalTime current = start;
        while (current.plusMinutes(SLOT_DURATION_MINUTES).compareTo(end) <= 0) {
            slots.add(new TimeInterval(current, current.plusMinutes(SLOT_DURATION_MINUTES)));
            current = current.plusMinutes(SLOT_DURATION_MINUTES);
        }
        return slots;
    }
}
