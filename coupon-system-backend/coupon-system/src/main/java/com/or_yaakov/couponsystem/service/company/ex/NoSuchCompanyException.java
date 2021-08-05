package com.or_yaakov.couponsystem.service.company.ex;

import com.or_yaakov.couponsystem.entity.Company;

/**
 * Thrown if {@link Company} doesn't exists or null.
 */
public class NoSuchCompanyException extends Exception {

    public NoSuchCompanyException() {
        super("Company doesn't exists!");
    }

    public NoSuchCompanyException(String message) {
        super(message);
    }
}
