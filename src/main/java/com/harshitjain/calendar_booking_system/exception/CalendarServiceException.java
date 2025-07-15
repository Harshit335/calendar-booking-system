package com.harshitjain.calendar_booking_system.exception;

public class CalendarServiceException extends RuntimeException {

    private final CalendarErrors type;

    public CalendarServiceException(CalendarErrors type) {
        super(type.getDescription());
        this.type = type;
    }

    public CalendarErrors getType() {
        return type;
    }
}
