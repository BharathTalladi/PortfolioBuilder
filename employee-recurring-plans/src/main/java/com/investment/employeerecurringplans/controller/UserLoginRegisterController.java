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

    @PostMapping("/register")
    private ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest){
        return ResponseEntity.ok(userLoginRegisterService.registerUser(registrationRequest));
    }

    @PostMapping("/login")
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userLoginRegisterService.authenticate(loginRequest));
    }
}
