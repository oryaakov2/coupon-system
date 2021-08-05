package com.or_yaakov.couponsystem.service.customer;

import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.entity.Customer;
import com.or_yaakov.couponsystem.rest.controller.ex.*;
import com.or_yaakov.couponsystem.service.customer.ex.NoSuchCustomerException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    /**
     * Find and retrieve {@link Customer} from data-source.
     *
     * @param customerId The id of {@link Customer}.
     * @return A {@link Optional} of {@link Customer}.
     */
    Optional<Customer> getCustomer(long customerId);

    /**
     * Update {@link Customer} in data-source.
     *
     * @param customerId The id of {@link Customer} that should be updated.
     * @param customer   The new {@link Customer}'s details.
     * @return A updated {@link Customer}.
     */
    Customer updateCustomer(long customerId, Customer customer) throws NoSuchCustomerException;

    /**
     * Find and retrieve all {@link Customer} coupons from data-source.
     *
     * @param id The id of specified {@link Customer}.
     * @return List of {@link Customer} coupons.
     */
    List<Coupon> getAllCoupons(long id)
            throws NoSuchCustomerException;

    /**
     * Find and retrieve {@link Customer} coupons from data-source by category.
     *
     * @param id       The id of specified {@link Customer}.
     * @param category The category of {@link Coupon}.
     * @return List of {@link Customer} coupons that include the same category.
     */
    List<Coupon> getAllCouponsByCategory(long id, int category)
            throws InvalidCouponCategoryException;

    /**
     * Find and retrieve {@link Customer} coupons from data-source that are less than parameter price.
     *
     * @param id    The id of specified {@link Customer}.
     * @param price The price of {@link Coupon}.
     * @return List of {@link Customer} coupons that have less price.
     */
    List<Coupon> getAllCouponsLessThan(long id, double price)
            throws InvalidCouponPriceException, NoSuchCustomerException;

    /**
     * Find and retrieve list of {@link Customer} coupons from data-source that before parameter date.
     *
     * @param id   The id of specified {@link Customer}.
     * @param date The end date of {@link Coupon}.
     * @return List of {@link Customer} coupons before end date.
     */
    List<Coupon> getAllCouponsBeforeDate(long id, LocalDate date)
            throws InvalidDateFormatException, NoSuchCustomerException;

    /**
     * Purchase a {@link Coupon} for {@link Customer}.
     *
     * @param customerId The id of {@link Customer}.
     * @param coupon     The {@link Coupon} that should be purchased.
     * @return A {@link Optional} of {@link Coupon}.
     * @throws NoSuchCouponException     Thrown if {@link Coupon} null or doesn't exists.
     * @throws ZeroCouponAmountException Thrown if Amount of {@link Coupon} equals to zero.
     */
    Coupon purchaseCoupon(long customerId, Coupon coupon)
            throws NoSuchCouponException, ZeroCouponAmountException,
            CouponAlreadyPurchasedException, NoSuchCustomerException;

    /**
     * Find and retrieve list of {@link Coupon} for purchase from the data-source
     *
     * @param customerId The id of {@link Customer}.
     * @return List of {@link Coupon}.
     */
    List<Coupon> getCouponsForPurchase(long customerId);

    List<Coupon> getCouponsBeforeExpired(long customerId)
            throws NoSuchCustomerException;
}