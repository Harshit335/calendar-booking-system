package com.harshitjain.calendar_booking_system.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

public class AvailableSlots {
    @Schema(description = "Start time of the available slot", example = "11:00")
    private String startTime;

    @Schema(description = "End time of the available slot", example = "12:00")
    private String endTime;

    public AvailableSlots(String startTime, String endTime) {
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
