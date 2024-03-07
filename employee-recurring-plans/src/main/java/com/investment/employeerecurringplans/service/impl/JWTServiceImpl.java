package com.investment.employeerecurringplans.service.impl;

import com.investment.employeerecurringplans.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    // Method to generate a JWT token
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24*60*1000)) // Token expires in 24 hours
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Method to retrieve the signing key
    private Key getSignKey() {
        // Decoding the key from Base64
        byte[] keyBytes= Decoders.BASE64.decode("63B39531B17AEB172F17958B82C95F4528482B4D6251655468576D5A71347437");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method to extract claims from the token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // Method to extract the username from the token
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // Method to validate the token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Method to check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
