package com.harshitjain.calendar_booking_system.repository;

import com.harshitjain.calendar_booking_system.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository {
    void save(Appointment appointment);
    List<Appointment> findByOwnerAndDate(String ownerEmail, LocalDate date);
    List<Appointment> findUpcoming(String ownerEmail, LocalDate today, LocalTime now);
    List<Appointment> findAllByOwner(String ownerEmail);
}
