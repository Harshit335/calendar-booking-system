package com.harshitjain.calendar_booking_system.Service;

import com.harshitjain.calendar_booking_system.DTOs.AppointmentRequest;
import com.harshitjain.calendar_booking_system.DTOs.AppointmentResponse;

public interface BookingService {
    AppointmentResponse bookAppointment(String ownerEmail, AppointmentRequest request);
}
