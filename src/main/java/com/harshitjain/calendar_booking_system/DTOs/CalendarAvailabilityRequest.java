package com.harshitjain.calendar_booking_system.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CalendarAvailabilityRequest {
    @NotBlank(message = "Start time is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Start time must be in HH:mm format")
    @Schema(description = "Start of availability window", example = "10:00")
    private String startTime;

    @NotBlank(message = "End time is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "End time must be in HH:mm format")
    @Schema(description = "End of availability window", example = "17:00")
    private String endTime;

    public CalendarAvailabilityRequest(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
}
