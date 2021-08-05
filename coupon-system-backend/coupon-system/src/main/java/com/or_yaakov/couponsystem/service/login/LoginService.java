package com.or_yaakov.couponsystem.service.login;

import com.or_yaakov.couponsystem.entity.Company;
import com.or_yaakov.couponsystem.entity.Customer;
import com.or_yaakov.couponsystem.rest.common.ClientSession;
import com.or_yaakov.couponsystem.service.login.ex.InvalidLoginException;

public interface LoginService {

    /**
     * Create session object for specific {@link Customer} that login to the system.
     *
     * @param email    The email of the {@link Customer}.
     * @param password The password of the {@link Customer}
     * @return A {@link ClientSession}
     * @throws InvalidLoginException If email and/or password incorrect.
     */
    ClientSession createCustomerSession(String email, String password) throws InvalidLoginException;

    /**
     * Create session object for specific {@link Company} that login to the system.
     *
     * @param email    The email of the {@link Company}.
     * @param password The password of the {@link Company}
     * @return A {@link ClientSession}
     * @throws InvalidLoginException If email and/or password incorrect.
     */
    ClientSession createCompanySession(String email, String password) throws InvalidLoginException;

    /**
     * Generate a random token for specific client in the system.
     *
     * @return A Random token for the client.
     */
    String generateToken();
}
