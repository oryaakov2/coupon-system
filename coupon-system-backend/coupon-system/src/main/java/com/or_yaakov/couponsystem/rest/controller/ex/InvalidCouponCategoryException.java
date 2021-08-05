package com.or_yaakov.couponsystem.rest.controller.ex;

import com.or_yaakov.couponsystem.entity.Coupon;

/**
 * Thrown if {@link Coupon}'s category less or equals to 0.
 */
public class InvalidCouponCategoryException extends Exception {

    public InvalidCouponCategoryException() {
        super("Invalid coupon category!");
    }

    public InvalidCouponCategoryException(String message) {
        super(message);
    }
}
