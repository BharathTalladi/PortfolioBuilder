package com.investment.employeerecurringplans.controller;

import com.investment.employeerecurringplans.model.LoginResponse;
import com.investment.employeerecurringplans.model.LoginRequest;
import com.investment.employeerecurringplans.model.RegistrationRequest;
import com.investment.employeerecurringplans.model.RegistrationResponse;
import com.investment.employeerecurringplans.service.UserLoginRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contributions")
@RequiredArgsConstructor
public class UserLoginRegisterController {

    private final UserLoginRegisterService userLoginRegisterService;

    // Endpoint for user registration
    @PostMapping("/register")
    private ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest){
        // Call the service to handle user registration and return the response
        return ResponseEntity.ok(userLoginRegisterService.registerUser(registrationRequest));
    }

    // Endpoint for user login
    @PostMapping("/login")
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        // Call the service to handle user authentication and return the response
        return ResponseEntity.ok(userLoginRegisterService.authenticate(loginRequest));
    }
}
