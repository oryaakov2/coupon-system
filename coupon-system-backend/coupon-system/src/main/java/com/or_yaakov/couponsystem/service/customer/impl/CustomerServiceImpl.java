package com.or_yaakov.couponsystem.service.customer.impl;

import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.entity.Customer;
import com.or_yaakov.couponsystem.repo.CouponRepository;
import com.or_yaakov.couponsystem.repo.CustomerRepository;
import com.or_yaakov.couponsystem.rest.controller.ex.*;
import com.or_yaakov.couponsystem.service.customer.CustomerService;
import com.or_yaakov.couponsystem.service.customer.ex.NoSuchCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CouponRepository couponRepository, CustomerRepository customerRepository) {
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Optional<Customer> getCustomer(long customerId) {
        if (customerRepository.findById(customerId).isPresent()) {
            return customerRepository.findById(customerId);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Customer updateCustomer(long customerId, Customer customer) throws NoSuchCustomerException {
        Optional<Customer> optCustomer = customerRepository.findById(customerId);

        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException();
        }
        customer.setId(customerId);
        customer.setCoupons(optCustomer.get().getCoupons());
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public List<Coupon> getAllCoupons(long customerId) throws NoSuchCustomerException {
        if (customerRepository.findById(customerId).isPresent()) {
            return couponRepository.findCustomerCoupons(customerId);
        }
        throw new NoSuchCustomerException();
    }


    @Override
    @Transactional
    public List<Coupon> getAllCouponsByCategory(long customerId, int category) throws InvalidCouponCategoryException {
        if (customerRepository.findById(customerId).isPresent() && category >= Coupon.MINIMUM_CATEGORY) {
            return couponRepository.findCustomerCouponsByCategory(customerId, category);
        }
        throw new InvalidCouponCategoryException();
    }

    @Override
    @Transactional
    public List<Coupon> getAllCouponsLessThan(long customerId, double price) throws InvalidCouponPriceException, NoSuchCustomerException {
        if (price < Coupon.MINIMUM_PRICE) {
            throw new InvalidCouponPriceException();

        } else if (customerRepository.findById(customerId).isEmpty()) {
            throw new NoSuchCustomerException();
        }
        return couponRepository.findCustomerCouponsLessThan(customerId, price);
    }

    @Override
    @Transactional
    public List<Coupon> getAllCouponsBeforeDate(long customerId, LocalDate date) throws InvalidDateFormatException, NoSuchCustomerException {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new InvalidDateFormatException();
        }
        if (customerRepository.findById(customerId).isPresent()) {
            return couponRepository.findCustomerCouponsBeforeDate(customerId, date);
        }
        throw new NoSuchCustomerException();
    }

    @Override
    @Transactional
    public Coupon purchaseCoupon(long customerId, Coupon coupon)
            throws NoSuchCouponException, ZeroCouponAmountException,
            NoSuchCustomerException, CouponAlreadyPurchasedException {

        if (coupon == null || couponRepository.findById(coupon.getId()).isEmpty()) {
            throw new NoSuchCouponException();
        }
        if (coupon.getAmount() <= Coupon.MINIMUM_AMOUNT) {
            throw new ZeroCouponAmountException();
        }

        Optional<Customer> optCustomer = customerRepository.findById(customerId);

        if (optCustomer.isPresent()) {
            Customer customer = optCustomer.get();

            if (customer.getCoupons().contains(coupon)) {
                throw new CouponAlreadyPurchasedException();
            }
            couponRepository.decrementCouponAmount(coupon.getId());
            customer.getCoupons().add(coupon);
            return coupon;
        }
        throw new NoSuchCustomerException();
    }

    @Override
    @Transactional
    public List<Coupon> getCouponsForPurchase(long customerId) {
        Optional<Customer> optCustomer = customerRepository.findById(customerId);
        List<Coupon> couponsForPurchase = new ArrayList<>();

        if (optCustomer.isPresent()) {
            List<Coupon> allCoupons = couponRepository.findAll();
            List<Coupon> customerCoupons = optCustomer.get().getCoupons();

            for (Coupon coupon : allCoupons) {
                if (!(customerCoupons.contains(coupon))) {
                    couponsForPurchase.add(coupon);
                }
            }
        }
        return couponsForPurchase;
    }

    @Override
    @Transactional
    public List<Coupon> getCouponsBeforeExpired(long customerId) throws NoSuchCustomerException {
        Optional<Customer> optCustomer = customerRepository.findById(customerId);

        if (optCustomer.isPresent()) {
            return couponRepository.findCustomerCouponsBeforeExpired(customerId);
        }
        throw new NoSuchCustomerException();
    }
}