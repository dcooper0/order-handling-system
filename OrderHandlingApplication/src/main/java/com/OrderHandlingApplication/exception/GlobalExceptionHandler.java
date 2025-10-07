package com.OrderHandlingApplication.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException e){
        logger.error("Existing customer with given name not found. ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<String> handleInvalidOrder(InvalidOrderException e) {
        logger.error("Invalid order. ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception e){
        logger.error("Unexpected error. ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + e.getClass() + " " + e.getMessage());
    }

    @ExceptionHandler(QueueProcessingException.class)
    public ResponseEntity<String> handleQueueError(QueueProcessingException e) {
        logger.error("Queue processing error. ", e);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Queue error: " + e.getMessage());
    }




}
