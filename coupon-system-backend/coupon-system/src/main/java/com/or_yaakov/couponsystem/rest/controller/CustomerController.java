package com.or_yaakov.couponsystem.rest.controller;

import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.entity.Customer;
import com.or_yaakov.couponsystem.rest.common.ClientSession;
import com.or_yaakov.couponsystem.rest.controller.ex.*;
import com.or_yaakov.couponsystem.service.TokensManager;
import com.or_yaakov.couponsystem.service.customer.CustomerService;
import com.or_yaakov.couponsystem.service.customer.ex.NoSuchCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;
    private final TokensManager tokensManager;

    @Autowired
    public CustomerController(CustomerService customerService, TokensManager tokensManager) {
        this.customerService = customerService;
        this.tokensManager = tokensManager;
    }

    @GetMapping()
    public ResponseEntity<Customer> getCustomer(@RequestParam String token)
            throws InvalidOrExpiredTokenException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.of(customerService.getCustomer(customerSession.getId()));
    }

    @PatchMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestParam String token, @RequestBody Customer customer)
            throws InvalidOrExpiredTokenException, NoSuchCustomerException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(customerService.updateCustomer(customerSession.getId(), customer));
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token)
            throws InvalidOrExpiredTokenException, NoSuchCustomerException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> allCustomerCoupons = customerService.getAllCoupons(customerSession.getId());

        return allCustomerCoupons.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(allCustomerCoupons);
    }

    @GetMapping("/coupons-category/{category}")
    public ResponseEntity<List<Coupon>> getAllCouponsByCategory(@RequestParam String token, @PathVariable int category)
            throws InvalidOrExpiredTokenException, InvalidCouponCategoryException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> allCouponsByCategory = customerService.getAllCouponsByCategory(customerSession.getId(), category);

        return allCouponsByCategory.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(allCouponsByCategory);
    }

    @GetMapping("/coupons-less/{price}")
    public ResponseEntity<List<Coupon>> getAllCouponsLessThan(@RequestParam String token, @PathVariable double price)
            throws InvalidOrExpiredTokenException, InvalidCouponPriceException, NoSuchCustomerException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> allCouponsLessThan = customerService.getAllCouponsLessThan(customerSession.getId(), price);

        return allCouponsLessThan.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(allCouponsLessThan);
    }


    @GetMapping("/coupons-before")
    public ResponseEntity<List<Coupon>> getAllCouponsBeforeDate(@RequestParam String token, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date)
            throws InvalidOrExpiredTokenException, InvalidDateFormatException, NoSuchCustomerException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> couponsBeforeDate = customerService.getAllCouponsBeforeDate(customerSession.getId(), date);

        return couponsBeforeDate.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(couponsBeforeDate);
    }

    @PostMapping("/purchase-coupons")
    public ResponseEntity<Coupon> purchaseCoupon(@RequestParam String token, @RequestBody Coupon coupon)
            throws InvalidOrExpiredTokenException, ZeroCouponAmountException, NoSuchCouponException,
            CouponAlreadyPurchasedException, NoSuchCustomerException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Coupon purchasedCoupon = customerService.purchaseCoupon(customerSession.getId(), coupon);

        return ResponseEntity.ok(purchasedCoupon);
    }

    @GetMapping("/coupons-for-purchase")
    public ResponseEntity<List<Coupon>> getCouponsForPurchase(@RequestParam String token)
            throws InvalidOrExpiredTokenException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(customerService.getCouponsForPurchase(customerSession.getId()));
    }

    @GetMapping("/coupons-before-expired")
    public ResponseEntity<List<Coupon>> getCouponsBeforeExpired(@RequestParam String token)
            throws InvalidOrExpiredTokenException, NoSuchCustomerException {

        ClientSession customerSession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(customerSession.getClientType().equals(ClientSession.CUSTOMER))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> couponsBeforeExpired = customerService.getCouponsBeforeExpired(customerSession.getId());

        return couponsBeforeExpired.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(couponsBeforeExpired);
    }
}