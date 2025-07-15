package com.harshitjain.calendar_booking_system.exception;

public enum CalendarErrors {
    AVAILABILITY_NOT_SET("Availability not set yet"),
    SLOT_ALREADY_BOOKED("Requested slot is already booked"),
    INVALID_TIME_RANGE("Start time must be before end time"),
    TIME_FORMAT_INVALID("Time must be in HH:00 format"),
    APPOINTMENT_IN_PAST("Cannot book an appointment in the past"),
    INVALID_INPUT("Invalid input provided"),
    RESOURCE_NOT_FOUND("Requested resource not found"),
    OPERATION_NOT_ALLOWED("Operation not allowed"),
    DATABASE_ERROR("Database error occurred"),
    EXTERNAL_SERVICE_FAILURE("External service failure");

    private final String description;

    CalendarErrors(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
