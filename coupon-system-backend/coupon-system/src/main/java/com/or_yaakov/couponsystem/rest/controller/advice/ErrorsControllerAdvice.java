package com.or_yaakov.couponsystem.rest.controller.advice;

import com.or_yaakov.couponsystem.rest.common.ErrorResponse;
import com.or_yaakov.couponsystem.rest.controller.ex.*;
import com.or_yaakov.couponsystem.service.company.ex.NoSuchCompanyException;
import com.or_yaakov.couponsystem.rest.controller.ex.CouponAlreadyPurchasedException;
import com.or_yaakov.couponsystem.service.customer.ex.NoSuchCustomerException;
import com.or_yaakov.couponsystem.service.login.ex.InvalidLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsControllerAdvice {

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidLogin(InvalidLoginException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(InvalidOrExpiredTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleInvalidOrExpiredToken(InvalidOrExpiredTokenException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(NoSuchCouponException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchCouponException(NoSuchCouponException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(NoSuchCustomerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchCustomerException(NoSuchCustomerException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(NoSuchCompanyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchCustomerException(NoSuchCompanyException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(ZeroCouponAmountException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleZeroCouponAmountException(ZeroCouponAmountException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(InvalidCouponCategoryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInvalidCategoryException(InvalidCouponCategoryException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInvalidDateFormatException(InvalidDateFormatException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(CouponAlreadyPurchasedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleCouponAlreadyPurchasedException(CouponAlreadyPurchasedException ex) {
        return ErrorResponse.from(ex);
    }
}