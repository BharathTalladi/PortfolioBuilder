package com.investment.employeerecurringplans.service;

import com.investment.employeerecurringplans.model.LoginResponse;
import com.investment.employeerecurringplans.model.LoginRequest;
import com.investment.employeerecurringplans.model.RegistrationRequest;
import com.investment.employeerecurringplans.model.RegistrationResponse;


public interface UserLoginRegisterService {
    RegistrationResponse registerUser(RegistrationRequest registrationRequest);
    LoginResponse authenticate(LoginRequest request);
}
