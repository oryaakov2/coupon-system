package com.or_yaakov.couponsystem.rest.controller.ex;

import com.or_yaakov.couponsystem.entity.Coupon;

/**
 * Thrown if {@link Coupon}'s price less or equals to 0.
 */
public class InvalidCouponPriceException extends Exception {

    public InvalidCouponPriceException() {
        super("Invalid coupon price!");
    }

    public InvalidCouponPriceException(String message) {
        super(message);
    }
}
