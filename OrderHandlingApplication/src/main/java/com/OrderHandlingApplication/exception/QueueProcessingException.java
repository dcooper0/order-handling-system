package com.OrderHandlingApplication.exception;

public class QueueProcessingException extends RuntimeException {

    public QueueProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
