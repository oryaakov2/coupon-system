package com.or_yaakov.couponsystem.rest.controller.ex;

import com.or_yaakov.couponsystem.entity.Coupon;

/**
 * Thrown if amount of {@link Coupon} equals to zero.
 */
public class ZeroCouponAmountException extends Exception {

    public ZeroCouponAmountException() {
        super("The coupon is out of stock!");
    }

    public ZeroCouponAmountException(String message) {
        super(message);
    }
}