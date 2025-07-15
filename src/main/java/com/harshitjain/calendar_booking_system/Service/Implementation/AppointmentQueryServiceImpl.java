package com.harshitjain.calendar_booking_system.Service.Implementation;

import com.harshitjain.calendar_booking_system.DTOs.AppointmentResponse;
import com.harshitjain.calendar_booking_system.Service.AppointmentQueryService;
import com.harshitjain.calendar_booking_system.model.Appointment;
import com.harshitjain.calendar_booking_system.repository.AppointmentRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentQueryServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AppointmentResponse> getAllAppointments(String ownerEmail) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return appointmentRepository.findUpcoming(ownerEmail, today, now)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDate(String ownerEmail, LocalDate date) {
        return appointmentRepository.findByOwnerAndDate(ownerEmail, date)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AppointmentResponse mapToResponse(Appointment app) {
        return new AppointmentResponse(
                app.getOwnerEmail(),
                app.getDate().toString(),
                app.getStartTime().toString(),
                app.getEndTime().toString(),
                app.getInviteeName(),
                app.getInviteeEmail(),
                app.getTitle()
        );
    }
}
