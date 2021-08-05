package com.or_yaakov.couponsystem.rest.common;

public class ErrorResponse {

    private final String message;
    private final long timestamp;

    private ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ErrorResponse from(Exception ex) {
        return new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
