package com.harshitjain.calendar_booking_system.model;

public class User {
    private String emailId;  // could be UUID or email
    private String name;
    private UserRole role;

    public User(String emailId, String name, UserRole role) {
        this.emailId = emailId;
        this.name = name;
        this.role = role;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }
}
