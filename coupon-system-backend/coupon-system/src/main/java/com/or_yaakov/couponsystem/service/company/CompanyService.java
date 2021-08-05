package com.or_yaakov.couponsystem.service.company;

import com.or_yaakov.couponsystem.entity.Company;
import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.rest.controller.ex.*;
import com.or_yaakov.couponsystem.service.company.ex.NoSuchCompanyException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompanyService {

    /**
     * Find and retrieve {@link Company} from data-source.
     *
     * @param companyId The id of {@link Company}.
     * @return A {@link Optional} of {@link Company}.
     */
    Optional<Company> getCompany(long companyId);

    /**
     * Update {@link Company} in data-source.
     *
     * @param companyId The id of {@link Company} that should be updated.
     * @param company   The new {@link Company}'s details.
     * @return A updated {@link Company}.
     */
    Company updateCompany(long companyId, Company company) throws NoSuchCompanyException;

    /**
     * Find and retrieve all {@link Company} coupons from data-source.
     *
     * @param companyId The id of specified {@link Company}.
     * @return List of {@link Company} coupons.
     */
    List<Coupon> getAllCoupons(long companyId) throws NoSuchCompanyException;

    /**
     * Find and retrieve {@link Company} coupons from data-source by category.
     *
     * @param companyId The id of specified {@link Company}.
     * @param category  The category of {@link Coupon}.
     * @return List of {@link Company} coupons that include the same category.
     */
    List<Coupon> getAllCouponsByCategory(long companyId, int category) throws InvalidCouponCategoryException;

    /**
     * Find and retrieve {@link Company} coupons from data-source that are less than parameter price.
     *
     * @param companyId The id of specified {@link Company}.
     * @param price     The price of {@link Coupon}.
     * @return List of {@link Company} coupons that have less price.
     */
    List<Coupon> getAllCouponsLessThan(long companyId, double price) throws InvalidCouponPriceException;

    /**
     * Find and retrieve list of {@link Company} coupons from data-source that before parameter date.
     *
     * @param companyId The id of specified {@link Company}.
     * @param date      The end date of {@link Coupon}.
     * @return List of {@link Company} coupons before end date.
     */
    List<Coupon> getAllCouponsBeforeDate(long companyId, LocalDate date) throws InvalidDateFormatException, NoSuchCompanyException;

    /**
     * Save {@link Coupon} for {@link Company} in data-source.
     *
     * @param coupon    The {@link Coupon} that should be saved.
     * @param companyId The id of {@link Company}.
     * @return A {@link Optional} of {@link Coupon}.
     */
    Coupon addCoupon(long companyId, Coupon coupon) throws NoSuchCouponException, NoSuchCompanyException;

    /**
     * Remove {@link Coupon} from data-source.
     *
     * @param companyId The id of {@link Company}.
     * @param coupon    The {@link Coupon} that should be removed.
     * @return A {@link Optional} of {@link Coupon}.
     */
    Coupon deleteCoupon(long companyId, Coupon coupon) throws NoSuchCouponException, NoSuchCompanyException;

    /**
     * Update {@link Company}'s {@link Coupon} in data-source.
     *
     * @param companyId The id of {@link Company}.
     * @param coupon    The {@link Coupon} that should be up-to date.
     * @return A {@link Optional} of {@link Coupon}.
     */
    Coupon updateCoupon(long companyId, Coupon coupon) throws NoSuchCouponException, NoSuchCompanyException;
}
