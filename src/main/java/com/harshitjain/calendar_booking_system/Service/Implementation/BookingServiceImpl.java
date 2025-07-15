package com.harshitjain.calendar_booking_system.Service.Implementation;

import com.harshitjain.calendar_booking_system.DTOs.AppointmentRequest;
import com.harshitjain.calendar_booking_system.DTOs.AppointmentResponse;
import com.harshitjain.calendar_booking_system.model.Appointment;
import com.harshitjain.calendar_booking_system.model.CalendarAvailability;
import com.harshitjain.calendar_booking_system.Service.AvailabilityService;
import com.harshitjain.calendar_booking_system.Service.BookingService;
import com.harshitjain.calendar_booking_system.exception.CalendarErrors;
import com.harshitjain.calendar_booking_system.exception.CalendarServiceException;
import com.harshitjain.calendar_booking_system.repository.AppointmentRepository;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final AvailabilityService availabilityService;
    private final AppointmentRepository appointmentRepository;
    private static final int SLOT_DURATION_MINUTES = 60;

    public BookingServiceImpl(AvailabilityService availabilityService, AppointmentRepository appointmentRepository) {
        this.availabilityService = availabilityService;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentResponse bookAppointment(String ownerEmail, AppointmentRequest request) {
        CalendarAvailability calendarAvailability = availabilityService.getAvailability(ownerEmail);
        if (calendarAvailability == null) {
            throw new CalendarServiceException(CalendarErrors.AVAILABILITY_NOT_SET);
        }

        LocalTime startTime;
        LocalDate date;

        try {
            startTime = LocalTime.parse(request.getStartTime());
            date = LocalDate.parse(request.getDate());
        } catch (DateTimeParseException ex) {
            throw new CalendarServiceException(CalendarErrors.TIME_FORMAT_INVALID);
        }

        if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now()))) {
            throw new CalendarServiceException(CalendarErrors.APPOINTMENT_IN_PAST);
        }

        if (startTime.getMinute() != 0) {
            throw new CalendarServiceException(CalendarErrors.TIME_FORMAT_INVALID);
        }

        LocalTime endTime = startTime.plusMinutes(SLOT_DURATION_MINUTES);

        if (startTime.isBefore(calendarAvailability.getStartTime()) ||
                endTime.isAfter(calendarAvailability.getEndTime()) ||
                endTime.isBefore(startTime)) {
            throw new CalendarServiceException(CalendarErrors.INVALID_TIME_RANGE);
        }

        // Use repository to find overlapping appointments
        List<Appointment> existingAppointments = appointmentRepository.findByOwnerAndDate(ownerEmail, date);

        boolean overlaps = existingAppointments.stream().anyMatch(app ->
                app.getStartTime().isBefore(endTime) && app.getEndTime().isAfter(startTime)
        );

        if (overlaps) {
            throw new CalendarServiceException(CalendarErrors.SLOT_ALREADY_BOOKED);
        }

        // Save appointment
        Appointment appointment = new Appointment(
                ownerEmail, date, startTime, endTime,
                request.getInviteeName(), request.getInviteeEmail(), request.getTitle()
        );

        appointmentRepository.save(appointment);

        return new AppointmentResponse(
                appointment.getOwnerEmail(),
                appointment.getDate().toString(),
                appointment.getStartTime().toString(),
                appointment.getEndTime().toString(),
                appointment.getInviteeName(),
                appointment.getInviteeEmail(),
                appointment.getTitle()
        );
    }
}