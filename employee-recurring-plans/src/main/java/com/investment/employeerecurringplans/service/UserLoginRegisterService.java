package com.investment.employeerecurringplans.service;

import com.investment.employeerecurringplans.model.LoginResponse;
import com.investment.employeerecurringplans.model.LoginRequest;
import com.investment.employeerecurringplans.model.RegistrationRequest;
import com.investment.employeerecurringplans.model.RegistrationResponse;

public interface UserLoginRegisterService {
    // Method to register a new user based on registration request
    RegistrationResponse registerUser(RegistrationRequest registrationRequest);

    // Method to authenticate user based on login request
    LoginResponse authenticate(LoginRequest request);
}
