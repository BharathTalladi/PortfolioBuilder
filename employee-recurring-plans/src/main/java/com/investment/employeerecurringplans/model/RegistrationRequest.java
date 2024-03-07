package com.investment.employeerecurringplans.model;

import com.investment.employeerecurringplans.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    // User's name
    private String name;

    // User's email ID
    private String emailId;

    // User's password
    private String password;

    // User's role (e.g., USER or ADMIN)
    @Enumerated(value = EnumType.STRING)
    private Role roles;
}
