package com.harshitjain.calendar_booking_system.model;

import java.time.LocalTime;
import java.time.LocalDate;

public class Appointment {
    private String ownerEmail;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String inviteeName;
    private String inviteeEmail;
    private String title;

    public Appointment(String ownerEmail, LocalDate date, LocalTime startTime, LocalTime endTime, String inviteeName, String inviteeEmail, String title) {
        this.ownerEmail = ownerEmail;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.inviteeName = inviteeName;
        this.inviteeEmail = inviteeEmail;
        this.title = title;    
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
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
    public String getInviteeName() {
        return inviteeName;
    }
    public void setInviteeName(String inviteeName) {
        this.inviteeName = inviteeName;
    }
    public String getInviteeEmail() {
        return inviteeEmail;
    }
    public void setInviteeEmail(String inviteeEmail) {
        this.inviteeEmail = inviteeEmail;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getOwnerEmail() {
        return ownerEmail;
    }
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
