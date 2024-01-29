package com.fetch.receiptprocessor.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fetch.receiptprocessor.model.ApiResponse;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = "The receipt is invalid";
            errors.put(fieldName, errorMessage);
        });
        ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidFormatException(InvalidFormatException ex) {
        // Check if the exception is due to a date format error
        String errorMessage;
        if (ex.getPathReference().contains("purchaseDate") || ex.getPathReference().contains("purchaseTime")) {
            errorMessage = "Invalid date or time format. Expected format: yyyy-MM-dd or HH:mm";
        } else {
            errorMessage = "Invalid format: " + ex.getMessage();
        }

        ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.badRequest().body(response);
    }
}
