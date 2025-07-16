
# Calendar Booking System

This is a modular calendar booking system built using **Java 21**, **Spring Boot**, and **Maven**.  
It allows multiple **calendar owners** to set their daily availability and lets **invitees** book 60-minute appointments without time conflicts.

---

## Features

- Calendar owners can define availability using fixed start and end times.
- Invitees can search for open 60-minute time slots.
- Appointment booking enforces:
  - No double booking
  - No booking in the past
  - Only valid 60-min time slots
- Retrieve all upcoming appointments or appointments by a specific date.

---

## Assumptions

- One Calendar per Owner: Each owner can set only one availability window (per day) and it applies to all future dates.

- Fixed Slot Duration: All appointments are exactly 60 minutes long and aligned on the hour (e.g., 10:00, 11:00).

- No Overlapping Appointments: A time slot becomes unavailable immediately after it is booked by an invitee.

- No Backdated Bookings: Appointments cannot be booked for a past date or time.

- Email as Primary ID: All users (owners or invitees) are identified by their email.

- In-Memory Storage: No database is used; all appointments and availability are stored in-memory using repository pattern.

- Stateless APIs: Each API request is independent and uses ownerEmail in the URL to operate on the correct calendar.

---

## Design Patterns & Architecture

- Repository Pattern

- Keeping core logic in Service Interfaces and Implementations

- Using Controller Layer for all RESTful endpoints

- Centralized Exception Handling

- DTOs for API Contracts

- Open/Closed Principle
---

## Tech Stack

- Java 21
- Spring Boot (Web, Validation)
- Springdoc OpenAPI for Swagger
- JUnit 5 + AssertJ + Hamcrest for testing
- Maven for build

---

## etup Instructions

### Prerequisites

- Java 21+
- Maven 3.8+
- Any IDE (IntelliJ / VS Code / Eclipse)

### Installation & Run

```bash
# Clone the repo
git clone https://github.com/Harshit335/calendar-booking-system.git

cd calendar-booking-system

# Build the project
mvn clean install

# Run the app
mvn spring-boot:run
```

---

## API Documentation (Swagger UI)

Once the app is running, visit:

> [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Running Tests

```bash
mvn test
```

Tests are located in:
```
src/test/java/com/harshitjain/calendar_booking_system/service/
```

Test coverage includes:

- Valid and invalid availability setup
- Booking rules (overlap, time validation, past checks)
- Slot availability logic
- pcoming and filtered appointment retrieval

---

## Project Structure

```
calendar-booking-system/
├── controller/
│   └── CalendarOwnerController.java
├── DTOs/
│   ├── AppointmentRequest.java
│   ├── AppointmentResponse.java
│   ├── AvailableSlots.java
│   └── CalendarAvailabilityRequest.java
├── exception/
│   ├── CalendarErrors.java
│   ├── CalendarServiceException.java
│   └── GlobalExceptionHandler.java
├── model/
│   ├── Appointment.java
│   ├── CalendarAvailability.java
│   ├── TimeInterval.java
│   ├── User.java
│   └── UserRole.java
├── repository/
│   ├── AppointmentRepository.java
│   ├── AvailabilityRepository.java
│   ├── InMemoryAppointmentRepository.java
│   └── InMemoryAvailabilityRepository.java
├── service/
│   ├── AvailabilityService.java
│   ├── BookingService.java
│   ├── SlotService.java
│   └── AppointmentQueryService.java
├── service/Implementation/
│   ├── AvailabilityServiceImpl.java
│   ├── BookingServiceImpl.java
│   ├── SlotServiceImpl.java
│   └── AppointmentQueryServiceImpl.java
├── CalendarBookingSystemApplication.java
└── README.md ✅
```

---

## 📌 Sample API Usage

### ✅ 1. Set Availability
```
POST /api/owners/harshit@mail.com/calendar
{
  "startTime": "10:00",
  "endTime": "17:00"
}
```

### ✅ 2. Get Available Slots
```
GET /api/owners/harshit@mail.com/available-slots?date=2025-07-18
```

### ✅ 3. Book Appointment
```
POST /api/owners/harshit@mail.com/book
{
  "startTime": "10:00",
  "date": "2025-07-18",
  "inviteeName": "Rajat",
  "inviteeEmail": "rajat@mail.com",
  "title": "Product Discussion"
}
```

### ✅ 4. Get All Upcoming Appointments
```
GET /api/owners/harshit@mail.com/appointments
```

### ✅ 5. Get Appointments by Date
```
GET /api/owners/harshit@mail.com/appointments-by-date?date=2025-07-18
```

---

## More Enhancements

- Add User Registration & Role-based access (using `User` class & `UserRole` enum)
