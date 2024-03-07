package com.investment.employeerecurringplans.service.impl;

import com.investment.employeerecurringplans.repository.UserRepository;
import com.investment.employeerecurringplans.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Override method to provide UserDetailsService
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
                // Load user by ID from the repository
                return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
