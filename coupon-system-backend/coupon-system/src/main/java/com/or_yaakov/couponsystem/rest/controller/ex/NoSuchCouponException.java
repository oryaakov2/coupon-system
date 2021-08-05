package com.or_yaakov.couponsystem.rest.controller.ex;

import com.or_yaakov.couponsystem.entity.Coupon;

/**
 * Thrown if {@link Coupon} doesn't exists or null.
 */
public class NoSuchCouponException extends Exception {

    public NoSuchCouponException() {
        super("Coupon doesn't exists!");
    }

    public NoSuchCouponException(String message) {
        super(message);
    }
}