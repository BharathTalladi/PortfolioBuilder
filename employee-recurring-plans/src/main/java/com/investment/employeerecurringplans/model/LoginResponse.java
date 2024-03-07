package com.investment.employeerecurringplans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    // A message indicating the status of the login attempt
    private String message;
    // A token generated upon successful login for authentication purposes
    private String token;
    // The role of the user logging in, such as 'USER' or 'ADMIN'
    private String role;
}
