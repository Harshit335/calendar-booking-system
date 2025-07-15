package com.harshitjain.calendar_booking_system.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AppointmentRequest {
    @NotBlank(message = "Start time is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Start time must be in HH:mm format")
    @Schema(description = "Start time of appointment", example = "10:00")
    private String startTime;

    @NotBlank(message = "Date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in yyyy-MM-dd format")
    @Schema(description = "Date of appointment", example = "2025-07-16")
    private String date;

    @NotBlank(message = "Invitee name is required")
    @Schema(description = "Invitee's name", example = "Harshit")
    private String inviteeName;
    
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email format")
    @Schema(description = "Invitee's email", example = "harshit@gmail.com")
    private String inviteeEmail;

    @NotBlank(message = "Details are required")
    @Schema(description = "Title of the appointment", example = "Test")
    private String title;

    public AppointmentRequest(String startTime, String date, String inviteeName, String inviteeEmail, String title) {
        this.startTime = startTime;
        this.date = date;
        this.inviteeName = inviteeName;
        this.inviteeEmail = inviteeEmail;
        this.title = title;
    }

    //Getters and Setters
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
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
}
