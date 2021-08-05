package com.or_yaakov.couponsystem.rest.controller.ex;

import java.util.Date;

/**
 * Thrown if {@link Date} is before the current {@link Date} or null.
 */
public class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException() {
        super("Invalid date!");
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
