package com.investment.employeerecurringplans.config;

import com.investment.employeerecurringplans.service.JWTService;
import com.investment.employeerecurringplans.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //Retrieving the Authorization Header from the Request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userId;
        // If Authorization header is missing or does not start with "Bearer ", continue to the next filter
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        // Extracting the JWT token from the Authorization header
        jwt= authHeader.substring(7);
        userId = jwtService.extractUsername(jwt);
        // If the userId is not null and no authentication is present in the SecurityContext, attempt to authenticate the user
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userId);
            // Validating the JWT token
            if(jwtService.validateToken(jwt, userDetails)) {
                // Create an authentication token for the user
                SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // Set additional authentication details
                token.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Create a new SecurityContext and set the authentication token
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // Continue to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
