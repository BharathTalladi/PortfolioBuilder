package com.investment.employeerecurringplans.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    // Method to provide UserDetailsService
    UserDetailsService userDetailsService();
}
