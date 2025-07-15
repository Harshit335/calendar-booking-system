package com.harshitjain.calendar_booking_system.repository;

import com.harshitjain.calendar_booking_system.model.Appointment;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class InMemoryAppointmentRepository implements AppointmentRepository {

    private final Map<String, List<Appointment>> store = new HashMap<>();

    @Override
    public void save(Appointment appointment) {
        store.computeIfAbsent(appointment.getOwnerEmail(), k -> new ArrayList<>()).add(appointment);
    }

    @Override
    public List<Appointment> findByOwnerAndDate(String ownerEmail, LocalDate date) {
        return store.getOrDefault(ownerEmail, List.of()).stream()
                .filter(app -> app.getDate().equals(date))
                .toList();
    }

    @Override
    public List<Appointment> findUpcoming(String ownerEmail, LocalDate today, LocalTime now) {
        return store.getOrDefault(ownerEmail, List.of()).stream()
                .filter(app -> app.getDate().isAfter(today) ||
                              (app.getDate().equals(today) && app.getStartTime().isAfter(now)))
                .toList();
    }

    @Override
    public List<Appointment> findAllByOwner(String ownerEmail) {
        return store.getOrDefault(ownerEmail, List.of());
    }
}