package com.or_yaakov.couponsystem.service.login.impl;

import com.or_yaakov.couponsystem.entity.Company;
import com.or_yaakov.couponsystem.entity.Customer;
import com.or_yaakov.couponsystem.repo.CompanyRepository;
import com.or_yaakov.couponsystem.repo.CustomerRepository;
import com.or_yaakov.couponsystem.rest.common.ClientSession;
import com.or_yaakov.couponsystem.service.login.LoginService;
import com.or_yaakov.couponsystem.service.login.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    public static final int LENGTH_TOKEN = 15;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public LoginServiceImpl(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public ClientSession createCustomerSession(String email, String password) throws InvalidLoginException {
        Optional<Customer> optCustomer = customerRepository.findByEmailAndPassword(email, password);

        if (optCustomer.isPresent()) {
            return ClientSession.of(optCustomer.get().getId(), ClientSession.CUSTOMER);
        }
        throw new InvalidLoginException();
    }

    @Override
    public ClientSession createCompanySession(String email, String password) throws InvalidLoginException {
        Optional<Company> optCompany = companyRepository.findByEmailAndPassword(email, password);

        if (optCompany.isPresent()) {
            return ClientSession.of(optCompany.get().getId(), ClientSession.COMPANY);
        }
        throw new InvalidLoginException();
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, LENGTH_TOKEN);
    }
}