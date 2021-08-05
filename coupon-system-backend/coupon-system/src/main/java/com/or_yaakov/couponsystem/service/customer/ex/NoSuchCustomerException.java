package com.or_yaakov.couponsystem.service.customer.ex;

import com.or_yaakov.couponsystem.entity.Customer;

/**
 * Thrown if the {@link Customer} doesn't exists.
 */
public class NoSuchCustomerException extends Exception {

    public NoSuchCustomerException() {
        super("Customer doesn't exists!");
    }

    public NoSuchCustomerException(String message) {
        super(message);
    }
}
