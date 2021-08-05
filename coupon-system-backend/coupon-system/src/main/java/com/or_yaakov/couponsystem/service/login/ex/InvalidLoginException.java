package com.or_yaakov.couponsystem.service.login.ex;

/**
 * Thrown if login failed.
 */
public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
        super("Wrong username or password.");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
