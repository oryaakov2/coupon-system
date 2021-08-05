package com.or_yaakov.couponsystem.rest.controller;

import com.or_yaakov.couponsystem.rest.common.ClientSession;
import com.or_yaakov.couponsystem.service.TokensManager;
import com.or_yaakov.couponsystem.service.login.LoginService;
import com.or_yaakov.couponsystem.service.login.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class LoginController {

    private final LoginService loginService;
    private final TokensManager tokensManager;

    @Autowired
    public LoginController(LoginService loginService, TokensManager tokensManager) {
        this.loginService = loginService;
        this.tokensManager = tokensManager;
    }

    @PostMapping("/login-customer")
    public ResponseEntity<String> loginCustomer(@RequestParam String email, @RequestParam String password)
            throws InvalidLoginException {

        String token = loginService.generateToken();
        ClientSession customerSession = loginService.createCustomerSession(email, password);

        tokensManager.put(token, customerSession);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login-company")
    public ResponseEntity<String> loginCompany(@RequestParam String email, @RequestParam String password)
            throws InvalidLoginException {

        String token = loginService.generateToken();
        ClientSession companySession = loginService.createCompanySession(email, password);

        tokensManager.put(token, companySession);

        return ResponseEntity.ok(token);
    }
}