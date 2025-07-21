/*

GlobalExceptionHandler.java
Author: Generated
Date: 21 July 2025 */
package za.co.admatech.controller;

import jakarta.persistence.EntityNotFoundException; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.ControllerAdvice; import org.springframework.web.bind.annotation.ExceptionHandler; import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    public static class ErrorResponse {
        private final int status;
        private final String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

}