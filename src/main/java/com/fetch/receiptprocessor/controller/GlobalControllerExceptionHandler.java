package com.fetch.receiptprocessor.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = "The receipt is invalid";
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
        // You can log the exception details, check the path reference, etc.

        // Check if the exception is due to a date format error
        if (ex.getPathReference().contains("purchaseDate") || ex.getPathReference().contains("purchaseTime")) {
            return ResponseEntity.badRequest().body("Invalid date or time format. Expected format: yyyy-MM-dd or HH:mm");
        }

        // Generic message for other invalid format errors
        return ResponseEntity.badRequest().body("Invalid format: " + ex.getMessage());
    }
}
