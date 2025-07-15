package com.harshitjain.calendar_booking_system.Service;

import com.harshitjain.calendar_booking_system.DTOs.AppointmentResponse;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentQueryService {
    List<AppointmentResponse> getAllAppointments(String ownerEmail);
    List<AppointmentResponse> getAppointmentsByDate(String ownerEmail, LocalDate date);
}

