package com.training.service.application;

/**
 * Exception class, customized
 */
public class Exceptions {

    public static class SessionNotFoundException extends RuntimeException {
        public SessionNotFoundException(String message) {
            super(message);
        }

        public SessionNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class SessionAlreadyCompletedException extends RuntimeException {
        public SessionAlreadyCompletedException(String message) {
            super(message);
        }
    }

    public static class EventPublishingException extends RuntimeException {
        public EventPublishingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}