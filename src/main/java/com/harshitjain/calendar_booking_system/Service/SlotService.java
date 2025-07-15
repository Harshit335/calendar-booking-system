package com.harshitjain.calendar_booking_system.Service;

import com.harshitjain.calendar_booking_system.DTOs.AvailableSlots;

import java.time.LocalDate;
import java.util.List;

public interface SlotService {
    List<AvailableSlots> getAvailableSlots(String ownerEmail, LocalDate date);
}