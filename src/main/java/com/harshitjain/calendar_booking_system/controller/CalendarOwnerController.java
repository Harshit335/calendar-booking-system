package com.harshitjain.calendar_booking_system.controller;

import com.harshitjain.calendar_booking_system.DTOs.*;
import com.harshitjain.calendar_booking_system.Service.*;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/owners")
@Tag(name = "Calendar Owner APIs", description = "Booking and availability endpoints per calendar owner")
public class CalendarOwnerController {
    private final AvailabilityService availabilityService;
    private final BookingService bookingService;
    private final SlotService slotService;
    private final AppointmentQueryService appointmentQueryService;

    public CalendarOwnerController(AvailabilityService availabilityService, BookingService bookingService, SlotService slotService, AppointmentQueryService appointmentQueryService) {
        this.availabilityService = availabilityService;
        this.bookingService = bookingService;
        this.slotService = slotService;
        this.appointmentQueryService = appointmentQueryService;
    }

    // ✅ 1. Set availability
    @Operation(
        summary = "Set or update daily availability window",
        requestBody = @RequestBody(
            required = true,
            description = "Availability time window",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Availability Request",
                    value = """
                    {
                        "startTime": "10:00",
                        "endTime": "17:00"
                    }
                    """
                )
            )
        )
    )
    @PostMapping("/{ownerEmail}/calendar")
    public void setAvailability(@PathVariable String ownerEmail, @RequestBody @Valid CalendarAvailabilityRequest request) {
        availabilityService.setAvailability(ownerEmail, request);
    }

    // ✅ 2. Get available slots
    @Operation(
        summary = "Get available 60-minute slots for a date",
        description = "Returns all available 60-minute time slots for a given date, excluding booked ones"
    )
    @GetMapping("/{ownerEmail}/available-slots")
    public List<AvailableSlots> getAvailableSlots(
            @PathVariable String ownerEmail,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return slotService.getAvailableSlots(ownerEmail, date);
    }

    // ✅ 3. Book an appointment
    @Operation(
        summary = "Book an appointment if the slot is available",
        requestBody = @RequestBody(
            description = "Appointment booking details",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Sample Booking Request",
                    value = """
                    {
                        "startTime": "10:00",
                        "date": "2025-07-16",
                        "inviteeName": "Harshit",
                        "inviteeEmail": "harashit@example.com",
                        "title": "Test"
                    }
                    """
                )
            )
        )
    )
    @PostMapping("/{ownerEmail}/book")
    public AppointmentResponse book(@PathVariable String ownerEmail, @RequestBody @Valid AppointmentRequest request) {
        return bookingService.bookAppointment(ownerEmail, request);
    }

    // ✅ 4. Get all upcoming appointments
    @Operation(summary = "List all upcoming appointments for the owner")
    @GetMapping("/{ownerEmail}/appointments")
    public List<AppointmentResponse> getAllAppointments(@PathVariable String ownerEmail) {
        return appointmentQueryService.getAllAppointments(ownerEmail);
    }

    // ✅ 5. Get appointments on a specific date
    @Operation(
        summary = "Get appointments by date",
        description = "Returns all appointments scheduled on a specific date",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(
                name = "date",
                description = "Date for which appointments are fetched",
                example = "2025-07-17"
            )
        }
    )
    @GetMapping("/{ownerEmail}/appointments-by-date")
    public List<AppointmentResponse> getAppointmentsByDate(@PathVariable String ownerEmail, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentQueryService.getAppointmentsByDate(ownerEmail, date);
    }
}
