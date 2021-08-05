package com.or_yaakov.couponsystem.rest.controller;

import com.or_yaakov.couponsystem.entity.Company;
import com.or_yaakov.couponsystem.entity.Coupon;
import com.or_yaakov.couponsystem.rest.common.ClientSession;
import com.or_yaakov.couponsystem.rest.controller.ex.*;
import com.or_yaakov.couponsystem.service.TokensManager;
import com.or_yaakov.couponsystem.service.company.CompanyService;
import com.or_yaakov.couponsystem.service.company.ex.NoSuchCompanyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin("http://localhost:3000")
public class CompanyController {

    private final CompanyService companyService;
    private final TokensManager tokensManager;

    @Autowired
    public CompanyController(CompanyService companyService, TokensManager tokensManager) {
        this.companyService = companyService;
        this.tokensManager = tokensManager;
    }

    @GetMapping()
    public ResponseEntity<Company> getCompany(@RequestParam String token)
            throws InvalidOrExpiredTokenException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.of(companyService.getCompany(companySession.getId()));
    }

    @PatchMapping("/update")
    public ResponseEntity<Company> updateCompany(@RequestParam String token, @RequestBody Company company)
            throws InvalidOrExpiredTokenException, NoSuchCompanyException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(companyService.updateCompany(companySession.getId(), company));
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token)
            throws InvalidOrExpiredTokenException, NoSuchCompanyException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> allCompanyCoupons = companyService.getAllCoupons(companySession.getId());

        return allCompanyCoupons.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(allCompanyCoupons);
    }

    @GetMapping("/coupons-category/{category}")
    public ResponseEntity<List<Coupon>> getAllCouponsByCategory(@RequestParam String token, @PathVariable int category)
            throws InvalidOrExpiredTokenException, InvalidCouponCategoryException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> allCouponsByCategory = companyService.getAllCouponsByCategory(companySession.getId(), category);

        return allCouponsByCategory.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(allCouponsByCategory);
    }

    @GetMapping("/coupons-less/{price}")
    public ResponseEntity<List<Coupon>> getAllCouponsLessThan(@RequestParam String token, @PathVariable double price)
            throws InvalidOrExpiredTokenException, InvalidCouponPriceException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> allCouponsLessThan = companyService.getAllCouponsLessThan(companySession.getId(), price);

        return allCouponsLessThan.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(allCouponsLessThan);
    }

    @GetMapping("/coupons-before")
    public ResponseEntity<List<Coupon>> getAllCouponsBeforeDate(@RequestParam String token, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date)
            throws InvalidOrExpiredTokenException, InvalidDateFormatException, NoSuchCompanyException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Coupon> allCouponsBeforeDate = companyService.getAllCouponsBeforeDate(companySession.getId(), date);

        return allCouponsBeforeDate.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(allCouponsBeforeDate);
    }

    @PostMapping("/add")
    public ResponseEntity<Coupon> addCoupon(@RequestParam String token, @RequestBody Coupon coupon)
            throws InvalidOrExpiredTokenException, NoSuchCouponException, NoSuchCompanyException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(companyService.addCoupon(companySession.getId(), coupon));
    }

    @PostMapping("/delete")
    public ResponseEntity<Coupon> removeCoupon(@RequestParam String token, @RequestBody Coupon coupon)
            throws InvalidOrExpiredTokenException, NoSuchCouponException, NoSuchCompanyException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(companyService.deleteCoupon(companySession.getId(), coupon));
    }

    @PatchMapping("/update-coupons")
    public ResponseEntity<Coupon> updateCoupon(@RequestParam String token, @RequestBody Coupon coupon)
            throws InvalidOrExpiredTokenException, NoSuchCouponException, NoSuchCompanyException {

        ClientSession companySession = tokensManager.accessOrElseThrow(token, InvalidOrExpiredTokenException::new);

        if (!(companySession.getClientType().equals(ClientSession.COMPANY))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(companyService.updateCoupon(companySession.getId(), coupon));
    }
}