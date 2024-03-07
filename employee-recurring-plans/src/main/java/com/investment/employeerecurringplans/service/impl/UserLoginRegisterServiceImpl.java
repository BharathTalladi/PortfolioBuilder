package com.investment.employeerecurringplans.service.impl;

import com.investment.employeerecurringplans.entity.Role;
import com.investment.employeerecurringplans.entity.User;
import com.investment.employeerecurringplans.model.LoginResponse;
import com.investment.employeerecurringplans.model.LoginRequest;
import com.investment.employeerecurringplans.model.RegistrationRequest;
import com.investment.employeerecurringplans.model.RegistrationResponse;
import com.investment.employeerecurringplans.repository.UserRepository;
import com.investment.employeerecurringplans.service.JWTService;
import com.investment.employeerecurringplans.service.UserLoginRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserLoginRegisterServiceImpl implements UserLoginRegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    // Method to generate a random 6-digit ID
    private String generateRandom6DigitId() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    // Method to register a new user
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        // Check if the user already exists
        Optional<User> existingUser = userRepository.findByEmailId(registrationRequest.getEmailId());
        if (existingUser.isPresent()) {
            return new RegistrationResponse(existingUser.get().getId(), "User already exists");
        }

        // Create a new user
        User registeringUser = new User();
        registeringUser.setId(generateRandom6DigitId());
        registeringUser.setName(registrationRequest.getName());
        registeringUser.setEmailId(registrationRequest.getEmailId());
        registeringUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        registeringUser.setRoles(registrationRequest.getRoles() != null ? registrationRequest.getRoles() : Role.USER);

        // Save the new user
        userRepository.save(registeringUser);
        return new RegistrationResponse(registeringUser.getId(), "User registration successful");
    }

    // Method to authenticate a user
    public LoginResponse authenticate(LoginRequest request) {
        // Check if the login credentials are present
        if (request.getId() == null || request.getPassword() == null) {
            return new LoginResponse("User login failed", "Login Credentials must be present", "Role cannot be provided");
        }

        // Retrieve the user by ID
        Optional<User> optionalUser = userRepository.findById(request.getId());
        if (optionalUser.isEmpty()) {
            return new LoginResponse("User login failed", "Cannot provide token", "Cannot provide role");
        }
        User user = optionalUser.get();
        String userPassword = user.getPassword();

        // Check if the password matches
        if (!passwordEncoder.matches(request.getPassword(), userPassword)) {
            return new LoginResponse("User login failed", "Incorrect password", "Role cannot be provided");
        }

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user.getId());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("User login successful");
        loginResponse.setToken(jwtToken);
        loginResponse.setRole(user.getRoles().name());
        return loginResponse;
    }

}
