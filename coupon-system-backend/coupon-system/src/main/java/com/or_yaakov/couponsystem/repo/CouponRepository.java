package com.or_yaakov.couponsystem.repo;

import com.or_yaakov.couponsystem.entity.Company;
import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * Find and retrieve all {@link Company} coupons from data-base.
     *
     * @param companyId The id of specified {@link Company}.
     * @return List of {@link Company} coupons.
     */
    List<Coupon> findAllByCompanyId(long companyId);

    /**
     * Find and retrieve {@link Company} coupons from data-base by category.
     *
     * @param companyId The id of specified {@link Company}.
     * @param category  The category of {@link Coupon}.
     * @return List of {@link Company} coupons that include the same category.
     */
    List<Coupon> findAllByCompanyIdAndCategory(long companyId, int category);

    /**
     * Find and retrieve {@link Company} coupons from data-base that are less than parameter price.
     *
     * @param companyId The id of specified {@link Company}.
     * @param price     The price of {@link Coupon}.
     * @return List of {@link Company} coupons that have less price.
     */
    List<Coupon> findAllByCompanyIdAndPriceLessThan(long companyId, double price);

    /**
     * Find and retrieve list of {@link Company} coupons from data-base that before parameter date.
     *
     * @param companyId The id of specified {@link Company}.
     * @param date      The end date of {@link Coupon}.
     * @return List of {@link Company} coupons before end date.
     */
    @Query("from Coupon "
           + "where company_Id =:companyId and end_Date <:date")
    List<Coupon> findAllCouponsBeforeDate(long companyId, LocalDate date);

    /**
     * Find and retrieve all {@link Customer} coupons from data-base.
     *
     * @param customerId The id of specified {@link Customer}.
     * @return List of {@link Customer} coupons.
     */
    @Query("select coupon from Coupon coupon "
           + "inner join "
           + "coupon.customers c "
           + "where c.id =:customerId")
    List<Coupon> findCustomerCoupons(long customerId);

    /**
     * Find and retrieve {@link Customer} coupons from data-base by category.
     *
     * @param customerId The id of specified {@link Customer}.
     * @param category   The category of {@link Coupon}.
     * @return List of {@link Customer} coupons that include the same category.
     */
    @Query("select coupon from Coupon coupon "
           + "inner join "
           + "coupon.customers c "
           + "where c.id =:customerId and coupon.category =:category")
    List<Coupon> findCustomerCouponsByCategory(long customerId, int category);

    /**
     * Find and retrieve {@link Customer} coupons from data-base that are less than parameter price.
     *
     * @param customerId The id of specified {@link Customer}.
     * @param price      The price of {@link Coupon}.
     * @return List of {@link Customer} coupons that have less price.
     */
    @Query("select coupon from Coupon coupon "
           + "inner join "
           + "coupon.customers c "
           + "where c.id =:customerId and coupon.price <:price")
    List<Coupon> findCustomerCouponsLessThan(long customerId, double price);

    /**
     * Find and retrieve list of {@link Customer} coupons from data-base that before parameter date.
     *
     * @param customerId The id of specified {@link Customer}.
     * @param date       The end date of {@link Coupon}.
     * @return List of {@link Customer} coupons before parameter date.
     */
    @Query("select coupon from Coupon coupon "
           + "inner join "
           + "coupon.customers c "
           + "where c.id =:customerId and coupon.endDate <:date")
    List<Coupon> findCustomerCouponsBeforeDate(long customerId, LocalDate date);

    /**
     * Find and retrieve all coupons from data-base with expired date.
     *
     * @return List of {@link Coupon}s with expired date.
     */
    @Query("select coupon from Coupon coupon "
           + "where coupon.endDate < CURRENT_DATE")
    List<Coupon> findExpiredCoupons();

    /**
     * Delete {@link Coupon} that related to {@link Company} from data-base.
     *
     * @param companyId The id of {@link Company}.
     * @param couponId  The id of {@link Coupon}.
     */
    @Modifying
    @Query("delete Coupon coupon "
           + "where coupon.company.id =:companyId and coupon.id =:couponId")
    void removeCompanyCoupon(long companyId, long couponId);

    /**
     * Decrement amount of {@link Coupon}.
     *
     * @param couponId The id of {@link Coupon}.
     */
    @Modifying
    @Query("update Coupon coupon "
           + "set coupon.amount = amount-1 where coupon.id =:couponId")
    void decrementCouponAmount(long couponId);

    @SuppressWarnings("SpellCheckingInspection")
    @Query(value = "select * from coupon t1 "
                   + "inner join "
                   + "customer_coupon t2 "
                   + "on t1.id = t2.coupon_id "
                   + "where t2.customer_id =:customerId "
                   + "and DATEDIFF(t1.end_date, CURDATE()) <= 7", nativeQuery = true)
    List<Coupon> findCustomerCouponsBeforeExpired(long customerId);
}