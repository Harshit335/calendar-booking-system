package com.harshitjain.calendar_booking_system.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

public class AppointmentResponse {
    @Schema(description = "Calendar owner's email", example = "harshit@mail.com")
    private String ownerEmail;

    @Schema(description = "Appointment date", example = "2025-07-17")
    private String date;

    @Schema(description = "Start time", example = "11:00")
    private String startTime;

    @Schema(description = "End time", example = "12:00")
    private String endTime;

    @Schema(description = "Name of the invitee", example = "Rajat")
    private String inviteeName;

    @Schema(description = "Email of the invitee", example = "rajat@mail.com")
    private String inviteeEmail;

    @Schema(description = "Title of the appointment", example = "Test")
    private String title;

    public AppointmentResponse(String ownerEmail, String date, String startTime, String endTime, String inviteeName, String inviteeEmail, String title){
        this.ownerEmail = ownerEmail;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.inviteeName = inviteeName;
        this.inviteeEmail = inviteeEmail;
        this.title = title;
    }

    public String getDate() {
        return date;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getInviteeName() {
        return inviteeName;
    }
    public String getInviteeEmail() {
        return inviteeEmail;
    }
    public String getTitle() {
        return title;
    }
    public String getOwnerEmail() {
        return ownerEmail;
    }
}
