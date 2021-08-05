package com.or_yaakov.couponsystem.rest.common;

import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponExpiredScheduler {
    private static final long MILLISECONDS_IN_ONE_DAY = 86_400_000L;

    private final CouponRepository couponRepository;

    @Autowired
    private CouponExpiredScheduler(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * Removes expired {@link Coupon} from data-base.
     */
    @Scheduled(fixedDelay = MILLISECONDS_IN_ONE_DAY)
    private void removesExpiredCoupons() {
        List<Coupon> expiredCoupons = couponRepository.findExpiredCoupons();

        if (!expiredCoupons.isEmpty()) {
            for (Coupon coupon : expiredCoupons) {
                couponRepository.delete(coupon);
            }
        }
    }
}