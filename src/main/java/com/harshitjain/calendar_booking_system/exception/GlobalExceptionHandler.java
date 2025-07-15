package com.harshitjain.calendar_booking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle @Valid validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(Map.of(
                "error", "Validation failed",
                "details", errors
        ));
    }

    // ✅ Handle business logic exceptions from CalendarService
    @ExceptionHandler(CalendarServiceException.class)
    public ResponseEntity<?> handleCalendarServiceException(CalendarServiceException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "errorType", ex.getType().name(),
                        "message", ex.getMessage()
                ));
    }

    // Handle any unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Something went wrong",
                "message", ex.getMessage()
        ));
    }
}
