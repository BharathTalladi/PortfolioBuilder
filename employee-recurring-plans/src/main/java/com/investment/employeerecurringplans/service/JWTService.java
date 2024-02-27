package com.investment.employeerecurringplans.service;

import com.investment.employeerecurringplans.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

//import java.util.Map;

public interface JWTService {
    String extractUsername(String token);
    String generateToken(String userId);
    boolean validateToken(String token, UserDetails userDetails);
    //String generateRefreshToken(Map<String,Object> extractClaims, UserDetails userDetails);
}
