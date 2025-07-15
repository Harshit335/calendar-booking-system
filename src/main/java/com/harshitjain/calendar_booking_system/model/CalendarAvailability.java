package com.harshitjain.calendar_booking_system.model;

import java.time.LocalTime;

public class CalendarAvailability {
    private String ownerEmail;
    private LocalTime startTime;
    private LocalTime endTime;


    public CalendarAvailability(String ownerEmail, LocalTime startTime, LocalTime endTime){
        this.ownerEmail = ownerEmail;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public String getOwnerEmail() {
        return ownerEmail;
    }
    public void setOwnerEmail(String ownerEmail){
        this.ownerEmail = ownerEmail;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setStartTime(LocalTime time){
        this.startTime = time;
    }
    public void setEndTime(LocalTime time){
        this.endTime = time;
    }
}
