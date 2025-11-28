package com.priya.otc.orchestrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFound(PaymentNotFoundException ex) {
        ErrorResponse resp = new ErrorResponse("PAYMENT_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse resp = new ErrorResponse("INTERNAL_ERROR", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class ErrorResponse {
        private String code;
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ErrorResponse() {
        }

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        // getters & setters
    }
}
