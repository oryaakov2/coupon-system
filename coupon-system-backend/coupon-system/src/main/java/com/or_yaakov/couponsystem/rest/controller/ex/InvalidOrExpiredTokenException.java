package com.or_yaakov.couponsystem.rest.controller.ex;

/**
 * Thrown if the token invalid or expired.
 */
public class InvalidOrExpiredTokenException extends Exception {

    public InvalidOrExpiredTokenException() {
        super("Your token invalid or expired!");
    }

    public InvalidOrExpiredTokenException(String message) {
        super(message);
    }
}