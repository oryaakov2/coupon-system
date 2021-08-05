package com.or_yaakov.couponsystem.service.company.impl;

import com.or_yaakov.couponsystem.entity.Company;
import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.repo.CompanyRepository;
import com.or_yaakov.couponsystem.repo.CouponRepository;
import com.or_yaakov.couponsystem.rest.controller.ex.*;
import com.or_yaakov.couponsystem.service.company.CompanyService;
import com.or_yaakov.couponsystem.service.company.ex.NoSuchCompanyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CouponRepository couponRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CouponRepository couponRepository, CompanyRepository companyRepository) {
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public Optional<Company> getCompany(long companyId) {
        if (companyRepository.findById(companyId).isPresent()) {
            return companyRepository.findById(companyId);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Company updateCompany(long companyId, Company company) throws NoSuchCompanyException {
        Optional<Company> optCompany = companyRepository.findById(companyId);

        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException();
        }
        company.setId(companyId);
        company.setCoupons(optCompany.get().getCoupons());
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public List<Coupon> getAllCoupons(long companyId) throws NoSuchCompanyException {
        if (companyRepository.findById(companyId).isPresent()) {
            return couponRepository.findAllByCompanyId(companyId);
        }
        throw new NoSuchCompanyException();
    }

    @Override
    @Transactional
    public List<Coupon> getAllCouponsByCategory(long companyId, int category) throws InvalidCouponCategoryException {
        if (companyRepository.findById(companyId).isPresent() && category >= Coupon.MINIMUM_CATEGORY) {
            return couponRepository.findAllByCompanyIdAndCategory(companyId, category);
        }
        throw new InvalidCouponCategoryException();
    }

    @Override
    @Transactional
    public List<Coupon> getAllCouponsLessThan(long companyId, double price) throws InvalidCouponPriceException {
        if (companyRepository.findById(companyId).isPresent() && price > Coupon.MINIMUM_PRICE) {
            return couponRepository.findAllByCompanyIdAndPriceLessThan(companyId, price);
        }
        throw new InvalidCouponPriceException();
    }

    @Override
    @Transactional
    public List<Coupon> getAllCouponsBeforeDate(long companyId, LocalDate date) throws InvalidDateFormatException, NoSuchCompanyException {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new InvalidDateFormatException();
        }
        if (companyRepository.findById(companyId).isPresent()) {
            return couponRepository.findAllCouponsBeforeDate(companyId, date);
        }
        throw new NoSuchCompanyException();
    }

    @Override
    @Transactional
    public Coupon addCoupon(long companyId, Coupon coupon) throws NoSuchCouponException, NoSuchCompanyException {
        if (coupon == null) {
            throw new NoSuchCouponException();
        }
        Optional<Company> optCompany = companyRepository.findById(companyId);

        if (optCompany.isPresent()) {
            coupon.setCompany(optCompany.get());
            return Optional.of(couponRepository.save(coupon)).get();
        }
        throw new NoSuchCompanyException();
    }

    @Override
    @Transactional
    public Coupon deleteCoupon(long companyId, Coupon coupon) throws NoSuchCouponException, NoSuchCompanyException {
        if (coupon == null || couponRepository.findById(coupon.getId()).isEmpty()) {
            throw new NoSuchCouponException();
        }
        Optional<Company> optCompany = companyRepository.findById(companyId);

        if (optCompany.isPresent() && optCompany.get().getCoupons().contains(coupon)) {
            couponRepository.removeCompanyCoupon(companyId, coupon.getId());
            return coupon;
        }
        throw new NoSuchCompanyException();
    }

    @Override
    @Transactional
    public Coupon updateCoupon(long companyId, Coupon coupon) throws NoSuchCouponException, NoSuchCompanyException {
        if (coupon == null || couponRepository.findById(coupon.getId()).isEmpty()) {
            throw new NoSuchCouponException();
        }
        Optional<Company> optCompany = companyRepository.findById(companyId);

        if (optCompany.isPresent() && optCompany.get().getCoupons().contains(coupon)) {
            coupon.setCompany(optCompany.get());

            Coupon sourceCoupon = couponRepository.findById(coupon.getId()).get();
            Coupon updatedCoupon = coupon.mergeCoupons(sourceCoupon);

            return couponRepository.save(updatedCoupon);
        }
        throw new NoSuchCompanyException();
    }
}