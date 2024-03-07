package com.investment.employeerecurringplans.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    // Method to extract username from JWT token
    String extractUsername(String token);

    // Method to generate JWT token for a given user ID
    String generateToken(String userId);

    // Method to validate JWT token against UserDetails
    boolean validateToken(String token, UserDetails userDetails);
}
