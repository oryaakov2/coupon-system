package com.or_yaakov.couponsystem.rest.controller.ex;

import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.entity.Customer;

/**
 * Thrown if the {@link Coupon} already owned by the {@link Customer}.
 */
public class CouponAlreadyPurchasedException extends Exception {

    public CouponAlreadyPurchasedException() {
        super("Coupon already owned by your account!");
    }

    public CouponAlreadyPurchasedException(String message) {
        super(message);
    }
}
